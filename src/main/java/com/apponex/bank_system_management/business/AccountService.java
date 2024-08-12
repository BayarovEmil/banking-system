package com.apponex.bank_system_management.business;

import com.apponex.bank_system_management.core.common.PageResponse;
import com.apponex.bank_system_management.core.exception.OperationNotPermittedException;
import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.core.util.CardGenerator;
import com.apponex.bank_system_management.dataAccess.AccountRepository;
import com.apponex.bank_system_management.dataAccess.CustomerRepository;
import com.apponex.bank_system_management.dto.account.AccountRequest;
import com.apponex.bank_system_management.dto.account.AccountResponse;
import com.apponex.bank_system_management.entity.account.Account;
import com.apponex.bank_system_management.entity.account.Card;
import com.apponex.bank_system_management.entity.account.CardType;
import com.apponex.bank_system_management.mapper.account.AccountMapper;
import com.apponex.bank_system_management.mapper.account.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final CardMapper cardMapper;
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    public AccountResponse createAccount(AccountRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        if (!customerRepository.existsByUser(user)) {
            throw new IllegalStateException("User is not customer");
        }
        Account account = Account.builder()
                .balance(BigDecimal.ZERO)
                .isActive(true)
                .card(cardMapper.generateCard(request.cardType()))
                .customer(user.getCustomer())
                .build();
        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    public AccountResponse getAccount(Authentication connectedUser,Integer accountId) {
        User user = (User) connectedUser.getPrincipal();
        if (!customerRepository.existsByUser(user)) {
            throw new IllegalStateException("User is not customer");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()->new IllegalStateException("Account not found by id"));
        if (!Objects.equals(account.getCustomer().getId(), user.getCustomer().getId())) {
            throw new OperationNotPermittedException("Cannot read other's account information");
        }
        return accountMapper.toAccountResponse(account);
    }

    public AccountResponse updateAccount(Authentication connectedUser, AccountRequest request,Integer accountId) {
        User user = (User) connectedUser.getPrincipal();
        if (!customerRepository.existsByUser(user)) {
            throw new IllegalStateException("User is not customer");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()->new IllegalStateException("Account not found by id"));
        account.setCard(cardMapper.generateCard(request.cardType()));
        if (!Objects.equals(account.getCustomer().getId(), user.getCustomer().getId())) {
            throw new OperationNotPermittedException("Cannot update other's account information");
        }
        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    public void deleteAccount(Authentication connectedUser,Integer accountId) {
        User user = (User) connectedUser.getPrincipal();
        if (!customerRepository.existsByUser(user)) {
            throw new IllegalStateException("User is not customer");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()->new IllegalStateException("Account not found by id"));
        if (!Objects.equals(account.getCustomer().getId(), user.getCustomer().getId())) {
            throw new OperationNotPermittedException("Cannot delete other's account information");
        }
        account.setActive(false);
        accountRepository.save(account);
    }

    public PageResponse<AccountResponse> getCustomerAccounts(int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        User user = (User) connectedUser.getPrincipal();
        Page<Account> accounts = accountRepository.findAllByCustomerId(user.getCustomer().getId(),pageable);
        List<AccountResponse> accountResponses = accounts.stream()
                .map(accountMapper::toAccountResponse)
                .toList();
        return new PageResponse<>(
                accountResponses,
                accounts.getNumber(),
                accounts.getSize(),
                accounts.getTotalElements(),
                accounts.getTotalPages(),
                accounts.isFirst(),
                accounts.isLast()
        );
    }
}
