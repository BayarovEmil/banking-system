package com.apponex.bank_system_management.dataAccess;

import com.apponex.bank_system_management.entity.payment.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionHistory,Integer> {
    Optional<Page<TransactionHistory>> findByAccountId(Integer accountId, Pageable pageable);

    Optional<Page<TransactionHistory>> findAllByAccountCustomerId(Integer customerId, Pageable pageable);

}
