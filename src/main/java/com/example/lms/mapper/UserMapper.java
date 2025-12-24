package com.example.lms.mapper;

import com.example.lms.dto.UserResponse;
import com.example.lms.dto.CreateUserRequest;
import com.example.lms.model.User;

public class UserMapper {
    
    /**
     * Преобразует сущность User в DTO для ответа.
     */
    public static UserResponse toResponse(User source) {
        UserResponse target = new UserResponse();
        target.setId(source.getId());
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setRole(source.getRole());
        
        return target;
    }

    /**
     * Создает новую сущность User на основе запроса.
     */
    public static User fromRequest(CreateUserRequest request) {
        User entity = new User();
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setRole(request.getRole());
        
        return entity;
    }
}
