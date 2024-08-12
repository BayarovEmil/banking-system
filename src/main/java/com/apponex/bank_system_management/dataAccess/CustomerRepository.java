package com.apponex.bank_system_management.dataAccess;

import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Boolean existsByUser(User user);

    Optional<Customer> findByUserId(Integer id);
}
