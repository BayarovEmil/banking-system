package com.apponex.bank_system_management.dto.customer;

import lombok.Builder;

@Builder
public record CustomerRequest(
        AddressRequest address
) {
}
