package com.apponex.bank_system_management.dto.contribution;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdateCategoryRequest(
        String categoryName,
        BigDecimal percentage
) {
}
