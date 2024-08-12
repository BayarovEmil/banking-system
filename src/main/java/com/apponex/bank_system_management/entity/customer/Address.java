package com.apponex.bank_system_management.entity.customer;

import com.apponex.bank_system_management.core.common.BaseEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {
    private String country;
    private String city;
    private String street;
    private String zipCode;

}
