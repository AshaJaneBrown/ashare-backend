package com.ashare.service;

import com.ashare.dto.UserDTO;
import com.ashare.mapper.UserMapper;
import com.ashare.model.User;
import com.ashare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO getCurrentUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return UserMapper.toDto(user);
    }
}