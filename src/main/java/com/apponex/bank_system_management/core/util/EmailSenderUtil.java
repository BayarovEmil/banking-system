package com.apponex.bank_system_management.core.util;

import com.apponex.bank_system_management.core.email.EmailService;
import com.apponex.bank_system_management.core.email.EmailTemplate;
import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.core.security.repository.ActivationCodeRepository;
import com.apponex.bank_system_management.core.security.model.ActivationCode;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EmailSenderUtil {
    private final EmailService emailService;
    private final ActivationCodeRepository activationCodeRepository;
    public void sendValidationEmail(User user) {
        String activationCode = generateAndSaveActivationCode(user);

        try {
            emailService.sendEmail(
                    user.getUsername(),
                    user.getEmail(),
                    EmailTemplate.ACTIVATE_ACCOUNT,
                    activationCode,
                    "Account activation"
            );
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateAndSaveActivationCode(User user) {
        String code = generateActivationCode(6);

        ActivationCode activationCode = ActivationCode.builder()
                .user(user)
                .code(code)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
        activationCodeRepository.save(activationCode);
        return code;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i=0;i<length;i++) {
            int randomIndex = secureRandom.nextInt(length);
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
