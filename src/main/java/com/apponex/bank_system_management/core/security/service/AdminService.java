package com.apponex.bank_system_management.core.security.service;

import com.apponex.bank_system_management.core.common.PageResponse;
import com.apponex.bank_system_management.core.security.dto.UserResponseDto;
import com.apponex.bank_system_management.core.security.mapper.UserMapper;
import com.apponex.bank_system_management.core.security.model.User;
import com.apponex.bank_system_management.core.security.model.role.Role;
import com.apponex.bank_system_management.core.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public PageResponse<UserResponseDto> getAllUsersInformation(int page,int size) {
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponseDto> userResponses = users.stream()
                .map(userMapper::toUserResponse)
                .toList();
        return new PageResponse<>(
                userResponses,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast()
        );
    }

    public UserResponseDto getUserInformation(Integer id) {
        var user = userRepository.findById(id)
               .orElseThrow(()->new UsernameNotFoundException("User not found by id"));
        return userMapper.toUserResponse(user);
    }

    public void deleteAccount(Integer id) {
        var user = userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("User not found by id"));
        userRepository.deleteById(user.getId());
    }

    public UserResponseDto setAdmin(Integer id) {
        var user = userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("User not found by this id"));
        user.setRole(Role.ADMIN);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponseDto setManager(Integer id) {
        var user = userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("User not found by this id"));
        user.setRole(Role.MANAGER);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponseDto setOfficer(Integer id) {
        var user = userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("User not found by this id"));
        user.setRole(Role.OFFICER);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
