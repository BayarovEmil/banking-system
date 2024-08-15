package com.apponex.bank_system_management.core.security.repository;

import com.apponex.bank_system_management.core.security.model.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsRepository extends JpaRepository<Sms,Integer> {
    Optional<Sms> findByVerificationCode(String verificationCode);
}
