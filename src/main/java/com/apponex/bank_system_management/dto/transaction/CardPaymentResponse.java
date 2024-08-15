package com.apponex.bank_system_management.dto.transaction;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record CardPaymentResponse(
        String cardNumber,
        BigDecimal amount,
        LocalDateTime timestamp
) {
}
