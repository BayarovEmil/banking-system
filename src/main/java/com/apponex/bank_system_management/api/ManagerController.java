package com.apponex.bank_system_management.api;

import com.apponex.bank_system_management.business.ManagerService;
import com.apponex.bank_system_management.dto.contribution.CategoryRequest;
import com.apponex.bank_system_management.dto.contribution.CategoryResponse;
import com.apponex.bank_system_management.dto.contribution.UpdateCategoryRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@Tag(name = "Manager Controller")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody CategoryRequest request
    ) {
        return ResponseEntity.ok(managerService.createCategory(request));
    }

    @GetMapping ("/readCategory")
    public ResponseEntity<CategoryResponse> readCategory(
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(managerService.readCategory());
    }

    @PatchMapping ("/updateCategory/{category-id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @RequestBody UpdateCategoryRequest request,
            @PathVariable("category-id") Integer categoryId
    ) {
        return ResponseEntity.ok(managerService.updateCategory(request,categoryId));
    }
}
