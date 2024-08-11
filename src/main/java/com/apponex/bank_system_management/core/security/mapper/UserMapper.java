package com.apponex.bank_system_management.core.security.mapper;

import com.apponex.bank_system_management.core.security.dto.UserResponseDto;
import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.core.security.model.role.Role;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserResponseDto toUserResponse(User user) {
        return UserResponseDto.builder()
               .firstname(user.getName())
                .lastname(user.getLastname())
               .email(user.getEmail())
               .phoneNumber(user.getPhoneNumber())
                .role(Role.USER)
               .build();
    }
}
