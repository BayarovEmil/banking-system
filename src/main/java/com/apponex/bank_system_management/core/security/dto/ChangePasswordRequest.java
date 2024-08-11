package com.apponex.bank_system_management.core.security.dto;

import com.apponex.bank_system_management.core.security.controller.validator.password.ValidPassword;
import lombok.Builder;

@Builder
public record ChangePasswordRequest(
        @ValidPassword
        String currentPassword,
        @ValidPassword
        String newPassword,
        @ValidPassword
        String confirmationPassword
) {
}
