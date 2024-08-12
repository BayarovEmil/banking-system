package com.apponex.bank_system_management.dto.customer;

import lombok.Builder;

@Builder
public record AddressRequest(
        String country,
        String city,
        String street,
        String zipCode
) {
}
