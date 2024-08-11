package com.apponex.bank_system_management.core.security.service;

import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.core.security.model.role.Role;
import com.apponex.bank_system_management.core.security.repository.ActivationCodeRepository;
import com.apponex.bank_system_management.core.security.repository.UserRepository;
import com.apponex.bank_system_management.core.security.dto.AuthenticationRequest;
import com.apponex.bank_system_management.core.security.dto.AuthenticationResponse;
import com.apponex.bank_system_management.core.security.dto.RegistrationRequest;
import com.apponex.bank_system_management.core.util.EmailSenderUtil;
import com.apponex.bank_system_management.core.util.IpUtil;
import com.apponex.bank_system_management.core.util.TokenUtil;
import com.apponex.bank_system_management.core.security.model.ActivationCode;
import com.apponex.bank_system_management.core.security.config.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailSenderUtil emailSenderUtil;
    private final TokenUtil tokenUtil;
    public void register(RegistrationRequest request, HttpServletRequest servletRequest) {
        String ipAddress = IpUtil.getClientIp(servletRequest);
        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .role(Role.USER)
                .enabled(false)
                .accountLocked(false)
                .ipAddress(ipAddress)
                .build();
        userRepository.save(user);
        emailSenderUtil.sendValidationEmail(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var claims = new HashMap<String,Object>();
        User user = (User) auth.getPrincipal();
        claims.put("fullName",user.getName());

        String accessToken = jwtService.generateToken(claims,user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenUtil.revokeAllUserTokens(user);
        tokenUtil.saveUserToken(user,accessToken,refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void activateAccount(String code) {
        ActivationCode activationCode = activationCodeRepository.findByCode(code)
                .orElseThrow(()->new EntityNotFoundException("Code not found"));
        if (LocalDateTime.now().isAfter(activationCode.getExpiresAt())) {
            emailSenderUtil.sendValidationEmail(activationCode.getUser());
            throw new IllegalStateException("Activation code has been expired!");
        }
        User user = userRepository.findByEmail(activationCode.getUser().getEmail())
                .orElseThrow(()->new UsernameNotFoundException("User not found by email"));
        user.setEnabled(true);
        activationCode.setValidatedAt(LocalDateTime.now());
        userRepository.save(user);
        activationCodeRepository.save(activationCode);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader==null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail!=null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow(()->new UsernameNotFoundException("User not found by email"));
            if (jwtService.isTokenValid(refreshToken,user)) {
                var accessToken = jwtService.generateToken(user);
                tokenUtil.revokeAllUserTokens(user);
                tokenUtil.saveUserToken(user,accessToken,refreshToken);
                var authResponse = AuthenticationResponse.builder()
                        .refreshToken(refreshToken)
                        .accessToken(accessToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}
