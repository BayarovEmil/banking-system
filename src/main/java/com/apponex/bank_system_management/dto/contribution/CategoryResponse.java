package com.apponex.bank_system_management.dto.contribution;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record CategoryResponse(
        String categoryName,
        BigDecimal percent
) {
}
