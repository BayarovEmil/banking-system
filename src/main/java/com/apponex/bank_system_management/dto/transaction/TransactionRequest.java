package com.apponex.bank_system_management.dto.transaction;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionRequest(
        Integer accountId,
        String receiverCardNumber,
        BigDecimal amount,
        String description
) {
}
