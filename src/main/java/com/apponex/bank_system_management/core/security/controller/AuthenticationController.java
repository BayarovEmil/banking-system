package com.apponex.bank_system_management.core.security.controller;

import com.apponex.bank_system_management.core.security.dto.AuthenticationRequest;
import com.apponex.bank_system_management.core.security.dto.AuthenticationResponse;
import com.apponex.bank_system_management.core.security.service.AuthenticationService;
import com.apponex.bank_system_management.core.security.dto.RegistrationRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request,
            HttpServletRequest servletRequest
    ) {
        authenticationService.register(request,servletRequest);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/confirm")
    public void confirm(
            @RequestParam("code") String code
    ) {
        authenticationService.activateAccount(code);
    }

    @GetMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request,response);
    }
}
