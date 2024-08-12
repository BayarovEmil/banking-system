package com.apponex.bank_system_management.dto.customer;

import com.apponex.bank_system_management.core.security.dto.UserResponseDto;
import lombok.Builder;

@Builder
public record CustomerResponse(
        UserResponseDto userResponseDto,
        AddressResponse addressResponse
) {
}
