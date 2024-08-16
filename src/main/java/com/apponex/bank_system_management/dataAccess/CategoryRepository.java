package com.apponex.bank_system_management.dataAccess;

import com.apponex.bank_system_management.entity.contribution.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
