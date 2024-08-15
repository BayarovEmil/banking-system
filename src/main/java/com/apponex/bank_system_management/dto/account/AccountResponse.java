package com.apponex.bank_system_management.dto.account;

import com.apponex.bank_system_management.dto.customer.CustomerResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountResponse(
        BigDecimal balance,
        boolean isActive,
        CardResponse cardResponse,
        CustomerResponse customerResponse

) {
}
