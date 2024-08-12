package com.apponex.bank_system_management.dataAccess;

import com.apponex.bank_system_management.entity.payment.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

}
