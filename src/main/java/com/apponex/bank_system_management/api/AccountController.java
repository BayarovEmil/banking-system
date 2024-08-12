package com.apponex.bank_system_management.api;

import com.apponex.bank_system_management.business.AccountService;
import com.apponex.bank_system_management.core.common.PageResponse;
import com.apponex.bank_system_management.dto.account.AccountRequest;
import com.apponex.bank_system_management.dto.account.AccountResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Tag(name = "Account Controller")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(
            @RequestBody AccountRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(accountService.createAccount(request,connectedUser));
    }

    @GetMapping("/getCustomer/{account-id}")
    public ResponseEntity<AccountResponse> getAccount(
            Authentication connectedUser,
            @PathVariable("account-id") Integer accountId
    ) {
        return ResponseEntity.ok(accountService.getAccount(connectedUser,accountId));
    }

    @PutMapping("/updateCustomer/{account-id}")
    public ResponseEntity<AccountResponse> updateAccount(
            Authentication connectedUser,
            @RequestBody AccountRequest customerRequest,
            @PathVariable("account-id") Integer accountId
    ) {
        return ResponseEntity.ok(accountService.updateAccount(connectedUser, customerRequest,accountId));
    }


    @PatchMapping("/deleteAccount/{account-id}")
    public void deleteAccount(
            Authentication connectedUser,
            @PathVariable("account-id") Integer accountId
    ) {
        accountService.deleteAccount(connectedUser,accountId);
    }

    @GetMapping("/getCustomerAccounts")
    public ResponseEntity<PageResponse<AccountResponse>> getCustomerAccounts(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(accountService.getCustomerAccounts(page,size,connectedUser));
    }
}
