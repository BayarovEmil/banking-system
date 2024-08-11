package com.apponex.bank_system_management.core.security.repository;

import com.apponex.bank_system_management.core.security.model.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode,Integer> {
    Optional<ActivationCode> findByCode(String code);
}
