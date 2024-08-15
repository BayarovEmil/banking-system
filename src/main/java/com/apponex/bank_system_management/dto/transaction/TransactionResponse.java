package com.apponex.bank_system_management.dto.transaction;

import com.apponex.bank_system_management.dto.account.AccountResponse;
import com.apponex.bank_system_management.entity.payment.TransactionStatus;
import com.apponex.bank_system_management.entity.payment.TransactionType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponse(
        LocalDateTime dateTime,
        BigDecimal amount,
        BigDecimal commission,
        String receiverCardNumber,
        String senderCardNumber,
        TransactionStatus transactionStatus,
        TransactionType transactionType,
        String description,
        AccountResponse accountResponse
) {
}
