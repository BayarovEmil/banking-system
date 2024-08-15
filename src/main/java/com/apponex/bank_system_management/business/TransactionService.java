package com.apponex.bank_system_management.business;

import com.apponex.bank_system_management.core.common.PageResponse;
import com.apponex.bank_system_management.core.security.dto.SuccessResponse;
import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.core.util.EmailSenderUtil;
import com.apponex.bank_system_management.dataAccess.AccountRepository;
import com.apponex.bank_system_management.dataAccess.TransactionRepository;
import com.apponex.bank_system_management.dto.transaction.*;
import com.apponex.bank_system_management.entity.account.Account;
import com.apponex.bank_system_management.entity.payment.TransactionHistory;
import com.apponex.bank_system_management.entity.payment.TransactionStatus;
import com.apponex.bank_system_management.entity.payment.TransactionType;
import com.apponex.bank_system_management.mapper.account.AccountMapper;
import com.apponex.bank_system_management.mapper.payment.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private static final BigDecimal commission = BigDecimal.valueOf(1.5);

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountMapper accountMapper;

    private final EmailSenderUtil emailSenderUtil;

    public TransactionResponse sendMoneyWithCardNumber(TransactionRequest request) {
        Account senderAccount = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver"));
        Account recipientAccount = accountRepository.findByCardCardNumber(request.receiverCardNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid card number"));
        if (senderAccount.getBalance().compareTo(request.amount()) < 0)
            throw new IllegalArgumentException("Insufficient funds");

        senderAccount.setBalance(senderAccount.getBalance().subtract(request.amount()));
        recipientAccount.setBalance(recipientAccount.getBalance().add(request.amount()));

        TransactionHistory transactionHistory = TransactionHistory.builder()
                .amount(request.amount())
                .success(true)
                .commission(commission)
                .ownAccount(Objects.equals(senderAccount.getId(),recipientAccount.getId()))
                .description(request.description())
                .transactionStatus(TransactionStatus.PENDING)
                .transactionType(TransactionType.TRANSFER)
                .amountDeletedCardNumber(senderAccount.getCard().getCardNumber())
                .amountReceiverCardNumber(request.receiverCardNumber())
                .receiverUsername(recipientAccount.getCustomer().getUser().getName())
                .account(senderAccount)
                .build();

        transactionRepository.save(transactionHistory);

        return TransactionResponse.builder()
                .dateTime(LocalDateTime.now())
                .amount(request.amount())
                .commission(commission)
                .description(request.description())
                .receiverCardNumber(request.receiverCardNumber())
                .senderCardNumber(senderAccount.getCard().getCardNumber())
                .transactionStatus(TransactionStatus.SUCCESS)
                .transactionType(TransactionType.PAYMENT)
                .accountResponse(accountMapper.toAccountResponse(senderAccount))
                .build();
    }

    public TransactionResponse sendMoneyToOwnAccount(TransactionAccountRequest request) {
        Account senderAccount = accountRepository.findById(request.senderAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver"));
        Account recipientAccount = accountRepository.findById(request.receiverAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid account"));
        if (senderAccount.getBalance().compareTo(request.amount()) < 0)
            throw new IllegalArgumentException("Insufficient funds");

        senderAccount.setBalance(senderAccount.getBalance().subtract(request.amount()));
        recipientAccount.setBalance(recipientAccount.getBalance().add(request.amount()));

        accountRepository.save(senderAccount);
        accountRepository.save(recipientAccount);

        TransactionHistory transactionHistory = TransactionHistory.builder()
                .amount(request.amount())
                .success(true)
                .commission(BigDecimal.ZERO)
                .ownAccount(Objects.equals(senderAccount.getId(),recipientAccount.getId()))
                .description(request.description())
                .transactionStatus(TransactionStatus.PENDING)
                .transactionType(TransactionType.TRANSFER)
                .amountDeletedCardNumber(senderAccount.getCard().getCardNumber())
                .amountReceiverCardNumber(recipientAccount.getCard().getCardNumber())
                .receiverUsername(recipientAccount.getCustomer().getUser().getName())
                .account(senderAccount)
                .build();
        transactionRepository.save(transactionHistory);

        return TransactionResponse.builder()
                .dateTime(LocalDateTime.now())
                .amount(request.amount())
                .commission(BigDecimal.ZERO)
                .description(request.description())
                .receiverCardNumber(recipientAccount.getCard().getCardNumber())
                .senderCardNumber(senderAccount.getCard().getCardNumber())
                .transactionStatus(TransactionStatus.SUCCESS)
                .transactionType(TransactionType.PAYMENT)
                .accountResponse(accountMapper.toAccountResponse(senderAccount))
                .build();
    }

    public CardPaymentResponse cardPaymentDetails(Authentication connectedUser, CardPaymentRequest request, BigDecimal amount) {
        User user = (User) connectedUser.getPrincipal();
        Account account = accountRepository.findByCardCardNumber(request.cardNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid card number"));
        if (account.getCard().getExpirationDate().equals(request.expirationDate())) {
            throw new IllegalArgumentException("Card expiration date is wrong");
        }
        if (!account.getCard().getCvv().equals(request.cvv())) {
            throw new IllegalArgumentException("Cvv is wrong");
        }

        //it's no user , because i don't buy number from twill
//        smsSender.saveAndSendSms(account,amount);
        //it's used instead of sending sms
        emailSenderUtil.sendValidationEmail(user);

        return CardPaymentResponse.builder()
                .cardNumber(request.cardNumber())
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .build();
    }



    public SuccessResponse confirmationPayment(Authentication connectedUser, String verificationCode) {
        User user = (User) connectedUser.getPrincipal();
//        smsSender.sendConfirmationAndBalanceOperation(user,verificationCode);
        return SuccessResponse.builder()
                .message("Operation completed successfully")
                .success(true)
                .build();
    }

    public PageResponse<TransactionResponse> getAllTransactionHistory(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<TransactionHistory> transactionHistories = transactionRepository.findAllByAccountCustomerId(user.getCustomer().getId(),pageable)
                .orElseThrow(()->new IllegalStateException("Transaction history not found"));
        List<TransactionResponse> transactionResponse = transactionHistories.stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();
        return new PageResponse<>(
                transactionResponse,
                transactionHistories.getNumber(),
                transactionHistories.getSize(),
                transactionHistories.getTotalElements(),
                transactionHistories.getTotalPages(),
                transactionHistories.isFirst(),
                transactionHistories.isLast()
        );
    }

    public PageResponse<TransactionResponse> getAccountTransactionHistory(int page, int size, Authentication connectedUser, Integer accountId) {
        User user = (User) connectedUser.getPrincipal();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()->new IllegalStateException("Account not found"));
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<TransactionHistory> transactionHistories =  transactionRepository.findByAccountId(accountId,pageable)
                .orElseThrow(()->new IllegalStateException("Account not found"));
        List<TransactionResponse> transactionResponse = transactionHistories.stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();
        if (!Objects.equals(account.getCustomer().getId(), user.getCustomer().getId())) {
            throw new IllegalArgumentException("User does not have access to this account");
        }

        return new PageResponse<>(
                transactionResponse,
                transactionHistories.getNumber(),
                transactionHistories.getSize(),
                transactionHistories.getTotalElements(),
                transactionHistories.getTotalPages(),
                transactionHistories.isFirst(),
                transactionHistories.isLast()
        );
    }


    public TransactionResponse payYourCommunal(Authentication connectedUser, Integer accountId, CommunalPaymentRequest request) {
        User user = (User) connectedUser.getPrincipal();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()->new IllegalStateException("Account not found"));
        if (!Objects.equals(account.getCustomer().getId(), user.getCustomer().getId())) {
            throw new IllegalArgumentException("User does not have access to this account");
        }
        if (account.getBalance().compareTo(request.amount())<0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(request.amount()));
        accountRepository.save(account);

        TransactionHistory transactionHistory = TransactionHistory.builder()
                .amount(request.amount())
                .success(true)
                .commission(BigDecimal.ZERO)
                .ownAccount(true)
                .description("Communal payment")
                .transactionStatus(TransactionStatus.SUCCESS)
                .transactionType(TransactionType.COMMUNAL_PAYMENT)
                .amountDeletedCardNumber(account.getCard().getCardNumber())
                .build();
        return transactionMapper.toTransactionResponse(transactionHistory);
    }
}
