package com.apponex.bank_system_management.core.security.controller;

import com.apponex.bank_system_management.core.common.PageResponse;
import com.apponex.bank_system_management.core.security.dto.UserResponseDto;
import com.apponex.bank_system_management.core.security.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@Tag(name = "Admin Controller")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
//@Hidden
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/getAllUsersInformation")
    public ResponseEntity<PageResponse<UserResponseDto>> getAllUsersInformation(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(adminService.getAllUsersInformation(page, size));
    }

    @PatchMapping("/assignRoleToUser/{user-id}")
    public ResponseEntity<UserResponseDto> assignRoleToUser(
            @PathVariable("user-id") Integer id
    ) {
        return ResponseEntity.ok(adminService.assignRoleToUser(id));
    }

    @GetMapping("/getUserInformation/{user-id}")
    public ResponseEntity<UserResponseDto> getUserInformation(
            @PathVariable("user-id") Integer id
    ) {
        return ResponseEntity.ok(adminService.getUserInformation(id));
    }

    @DeleteMapping("/deleteAccount/{user-id}")
    public void deleteAccount(
            @PathVariable("user-id") Integer id
    ) {
        adminService.deleteAccount(id);
    }
}
