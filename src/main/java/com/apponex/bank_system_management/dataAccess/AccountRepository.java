package com.apponex.bank_system_management.dataAccess;

import com.apponex.bank_system_management.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer> {
}
