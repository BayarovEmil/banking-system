package com.apponex.bank_system_management.dataAccess;

import com.apponex.bank_system_management.entity.contribution.Cashback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashbackRepository extends JpaRepository<Cashback,Integer> {

    Optional<Cashback> findByCategoriesCategoryName(String categoryName);
}
