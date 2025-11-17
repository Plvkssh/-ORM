package com.example.lms.mapper;

import com.example.lms.dto.UserResponse;
import com.example.lms.dto.CreateUserRequest;
import com.example.lms.model.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }

    public static User fromRequest(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        return user;
    }
}
