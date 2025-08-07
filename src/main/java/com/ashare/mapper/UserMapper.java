package com.ashare.mapper;

import com.ashare.dto.UserDTO;
import com.ashare.model.User;

public class UserMapper {
    public static UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getBio()
        );
    }
}
