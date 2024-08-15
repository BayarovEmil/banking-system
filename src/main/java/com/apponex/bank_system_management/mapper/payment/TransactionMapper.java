package com.apponex.bank_system_management.mapper.payment;

import com.apponex.bank_system_management.dto.account.AccountResponse;
import com.apponex.bank_system_management.dto.transaction.TransactionResponse;
import com.apponex.bank_system_management.entity.payment.TransactionHistory;
import com.apponex.bank_system_management.mapper.account.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionMapper {
    private final AccountMapper accountMapper;

    public TransactionResponse toTransactionResponse(TransactionHistory transactionHistory) {
        return TransactionResponse.builder()
                .dateTime(transactionHistory.getCreatedDate())
                .amount(transactionHistory.getAmount())
                .commission(transactionHistory.getCommission())
                .receiverCardNumber(transactionHistory.getAmountReceiverCardNumber())
                .senderCardNumber(transactionHistory.getAmountDeletedCardNumber())
                .description(transactionHistory.getDescription())
                .transactionStatus(transactionHistory.getTransactionStatus())
                .transactionType(transactionHistory.getTransactionType())
                .accountResponse(accountMapper.toAccountResponse(transactionHistory.getAccount()))
                .build();
    }
}
