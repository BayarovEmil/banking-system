package com.apponex.bank_system_management.core.security.controller;

import com.apponex.bank_system_management.core.security.dto.ChangePasswordRequest;
import com.apponex.bank_system_management.core.security.service.UserService;
import com.apponex.bank_system_management.core.security.controller.validator.password.ValidPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/password/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication connectedUser
    ) {
        userService.changePassword(request,connectedUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password/forgetPassword")
    public ResponseEntity<?> forgetPassword(
            Authentication connectedUser
    ) {
        userService.forgetPassword(connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/password/resetPassword")
    public ResponseEntity<?> resetPassword(
            Authentication connectedUser,
            @RequestParam String code,
            @RequestParam @ValidPassword String newPassword,
            @RequestParam String confirmationPassword
    ) {
        userService.resetPassword(connectedUser,code,newPassword,confirmationPassword);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/deactivatedAccount")
    public ResponseEntity<?> deactivateAccount(
            Authentication connectedUser
    ) {
        userService.deactivateAccount(connectedUser);
        return ResponseEntity.ok().build();
    }

}
