package com.apponex.bank_system_management.mapper.customer;

import com.apponex.bank_system_management.dto.customer.AddressRequest;
import com.apponex.bank_system_management.dto.customer.AddressResponse;
import com.apponex.bank_system_management.dto.customer.CustomerRequest;
import com.apponex.bank_system_management.entity.customer.Address;
import com.apponex.bank_system_management.entity.customer.Customer;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {
    public Address toAddress(CustomerRequest request) {
        return Address.builder()
                .street(request.address().street())
                .city(request.address().city())
                .country(request.address().country())
                .zipCode(request.address().zipCode())
                .build();
    }

    public AddressRequest toAddressRequest(Address request) {
        return AddressRequest.builder()
                .street(request.getStreet())
                .city(request.getCity())
                .country(request.getCountry())
                .zipCode(request.getZipCode())
                .build();
    }

    public AddressResponse toAddressResponse(CustomerRequest request) {
        return AddressResponse.builder()
                .street(request.address().street())
                .city(request.address().city())
                .country(request.address().country())
                .zipCode(request.address().zipCode())
                .build();
    }
}
