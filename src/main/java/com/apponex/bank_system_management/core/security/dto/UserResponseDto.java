package com.apponex.bank_system_management.core.security.dto;

import com.apponex.bank_system_management.core.security.model.role.Role;
import lombok.Builder;

@Builder
public record UserResponseDto(
        String firstname,
        String lastname,
        String email,
        String  phoneNumber,
        Role role
) {
}
