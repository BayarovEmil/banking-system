package com.apponex.bank_system_management.dto.transaction;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record CardPaymentRequest(
        String cardNumber,
        String cvv,
        LocalDate expirationDate
) {
}
