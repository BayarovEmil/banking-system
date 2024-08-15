package com.apponex.bank_system_management.dto.transaction;

import java.math.BigDecimal;

public record CommunalPaymentRequest(
        BigDecimal amount,
        String category,
        String subscriberCode
) {
}
