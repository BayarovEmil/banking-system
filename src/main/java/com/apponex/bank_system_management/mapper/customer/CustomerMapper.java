package com.apponex.bank_system_management.mapper.customer;

import com.apponex.bank_system_management.core.security.mapper.UserMapper;
import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.dto.customer.CustomerRequest;
import com.apponex.bank_system_management.dto.customer.CustomerResponse;
import com.apponex.bank_system_management.entity.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerMapper {
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    public CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .addressResponse(addressMapper.toAddressResponse(toCustomerRequest(customer)))
                .userResponseDto(userMapper.toUserResponse(customer.getUser()))
                .build();
    }

    public CustomerRequest toCustomerRequest(Customer request) {
        return CustomerRequest.builder()
                .address(addressMapper.toAddressRequest(request.getAddress()))
                .build();
    }

    public Customer toCustomer(User user,CustomerRequest request) {
        return Customer.builder()
                .id(user.getCustomer().getId())
                .address(addressMapper.toAddress(request))
                .user(user)
                .build();
    }

}
