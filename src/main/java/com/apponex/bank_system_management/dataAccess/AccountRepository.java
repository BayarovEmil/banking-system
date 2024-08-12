package com.apponex.bank_system_management.dataAccess;

import com.apponex.bank_system_management.entity.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByCustomerId(Integer customerId);
    Page<Account> findAllByCustomerId(Integer customerId, Pageable pageable);
}
