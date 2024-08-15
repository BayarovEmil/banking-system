package com.apponex.bank_system_management.dto.transaction;

import java.math.BigDecimal;

public record TransactionAccountRequest(
        Integer senderAccountId,
        Integer receiverAccountId,
        BigDecimal amount,
        String description,
        String type,
        String currency
) {
}
