package com.apponex.bank_system_management.business;

import com.apponex.bank_system_management.core.exception.OperationNotPermittedException;
import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.core.security.model.role.Role;
import com.apponex.bank_system_management.core.security.repository.UserRepository;
import com.apponex.bank_system_management.core.security.service.UserService;
import com.apponex.bank_system_management.dataAccess.CustomerRepository;
import com.apponex.bank_system_management.dto.customer.CustomerRequest;
import com.apponex.bank_system_management.dto.customer.CustomerResponse;
import com.apponex.bank_system_management.entity.customer.Address;
import com.apponex.bank_system_management.entity.customer.Customer;
import com.apponex.bank_system_management.mapper.AddressMapper;
import com.apponex.bank_system_management.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;

    private final UserService userService;
    private final UserRepository userRepository;
    public CustomerResponse createCustomer(CustomerRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        if (customerRepository.existsByUser(user)) {
            throw new OperationNotPermittedException("Customer already exists");
        }
        Customer customer = Customer.builder()
                .user(user)
                .address(addressMapper.toAddress(request))
                .build();
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    public CustomerResponse getCustomer(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        var customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(()->new IllegalStateException("Customer not found"));
        return customerMapper.toCustomerResponse(customer);
    }

    public CustomerResponse updateCustomer(Authentication connectedUser, CustomerRequest customerRequest) {
        User user = (User) connectedUser.getPrincipal();
        if (!customerRepository.existsByUser(user) || user.isAccountLocked()) {
            throw new OperationNotPermittedException("Customer not found");
        }
        return customerMapper.toCustomerResponse(customerRepository.save(
                customerMapper.toCustomer(user,customerRequest)));
    }

    public void deleteCustomer(Authentication connectedUser) {
        userService.deactivateAccount(connectedUser);
    }
}
