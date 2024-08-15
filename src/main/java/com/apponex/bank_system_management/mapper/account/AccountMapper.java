package com.apponex.bank_system_management.mapper.account;

import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.dto.account.AccountRequest;
import com.apponex.bank_system_management.dto.account.AccountResponse;
import com.apponex.bank_system_management.dto.account.CardResponse;
import com.apponex.bank_system_management.dto.customer.CustomerResponse;
import com.apponex.bank_system_management.entity.account.Account;
import com.apponex.bank_system_management.mapper.customer.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountMapper {
    private final CardMapper cardMapper;
    private final CustomerMapper customerMapper;

    public AccountResponse toAccountResponse(Account account) {
        return AccountResponse.builder()
                .balance(account.getBalance())
                .isActive(account.isActive())
                .cardResponse(cardMapper.toCardResponse(account))
                .customerResponse(customerMapper.toCustomerResponse(account.getCustomer()))
                .build();
    }
}
