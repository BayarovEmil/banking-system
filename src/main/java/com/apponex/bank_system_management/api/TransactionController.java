package com.apponex.bank_system_management.api;

import com.apponex.bank_system_management.business.TransactionService;
import com.apponex.bank_system_management.core.common.PageResponse;
import com.apponex.bank_system_management.core.security.dto.SuccessResponse;
import com.apponex.bank_system_management.dto.transaction.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction Controller")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/send/cardNumber")
    public ResponseEntity<TransactionResponse> sendMoneyWithCardNumber(
            @RequestBody TransactionRequest request
    ) {
        return ResponseEntity.ok(transactionService.sendMoneyWithCardNumber(request));
    }

    @PostMapping("/send/sendMoneyToOwnAccount")
    public ResponseEntity<TransactionResponse> sendMoneyToOwnAccount(
            @RequestBody TransactionAccountRequest request
    ) {
        return ResponseEntity.ok(transactionService.sendMoneyToOwnAccount(request));
    }

    @PostMapping("/cardPaymentDetails")
    public ResponseEntity<CardPaymentResponse> cardPaymentDetails(
            Authentication connectedUser,
            @RequestBody CardPaymentRequest request,
            BigDecimal amount
    ) {
        return ResponseEntity.ok(transactionService.cardPaymentDetails(connectedUser,request,amount));
    }

    @PostMapping("/cardPaymentDetails/{verification-code}")
    public ResponseEntity<SuccessResponse> confirmationPayment(
            Authentication connectedUser,
            @PathVariable("verification-code") String verificationCode
    ) {
        return ResponseEntity.ok(transactionService.confirmationPayment(connectedUser,verificationCode));
    }

    @GetMapping("/getAccountTransactionHistory/{account-id}")
    public ResponseEntity<PageResponse<TransactionResponse>> getAccountTransactionHistory(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser,
            @PathVariable("account-id") Integer accountId
    ) {
        return ResponseEntity.ok(transactionService.getAccountTransactionHistory(page,size, connectedUser,accountId));
    }

    @GetMapping("/getAllTransactionHistory")
    public ResponseEntity<PageResponse<TransactionResponse>> getAllTransactionHistory(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(transactionService.getAllTransactionHistory(page,size,connectedUser));
    }

    @PostMapping("/payYourCommunal/{account-id}")
    public ResponseEntity<TransactionResponse> payYourCommunal(
            Authentication connectedUser,
            @RequestBody CommunalPaymentRequest request,
            @PathVariable("account-id") Integer accountId
    ) {
        return ResponseEntity.ok(transactionService.payYourCommunal(connectedUser,accountId,request));
    }

    


}
