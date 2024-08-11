package com.apponex.bank_system_management.core.util;

import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.core.security.model.token.TokenType;
import com.apponex.bank_system_management.core.security.repository.TokenRepository;
import com.apponex.bank_system_management.core.security.model.token.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenUtil {
    private final TokenRepository tokenRepository;
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
    }

    public void saveUserToken(User savedUser, String accessToken, String refreshToken) {
        Token token = Token.builder()
                .user(savedUser)
                .token(accessToken)
                .refreshToken(refreshToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

}
