package com.apponex.bank_system_management.core.security.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {
    private String message;
    private boolean success;
    public SuccessResponse(String message) {
        this.message = message;
        this.success = true;
    }
}
