package com.apponex.bank_system_management.api;

import com.apponex.bank_system_management.business.CustomerService;
import com.apponex.bank_system_management.dto.customer.CustomerRequest;
import com.apponex.bank_system_management.dto.customer.CustomerResponse;
import com.apponex.bank_system_management.entity.customer.Customer;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@Tag(name = "Customer Controller")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(
            @RequestBody CustomerRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(customerService.createCustomer(request,connectedUser));
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<CustomerResponse> getCustomer(
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(customerService.getCustomer(connectedUser));
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<CustomerResponse> updateCustomer(
            Authentication connectedUser,
            @RequestBody CustomerRequest customerRequest
    ) {
        return ResponseEntity.ok(customerService.updateCustomer(connectedUser, customerRequest));
    }


    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<Customer> deleteCustomer(
            Authentication connectedUser
    ) {
        customerService.deleteCustomer(connectedUser);
        return ResponseEntity.ok().build();
    }

}
