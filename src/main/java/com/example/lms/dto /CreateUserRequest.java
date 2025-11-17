package com.example.lms.dto;

import com.example.lms.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateUserRequest {
    @NotBlank(message = "Имя пользователя обязательно для заполнения")
    private String name;

    @Email(message = "Некорректный формат email")
    @NotBlank(message = "Email обязателен для заполнения")
    private String email;

    @NotNull(message = "Роль пользователя должна быть указана")
    private UserRole role;

    // Конструкторы
    public CreateUserRequest() {}
    
    public CreateUserRequest(String name, String email, UserRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Геттеры и сеттеры
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public UserRole getRole() { 
        return role; 
    }
    
    public void setRole(UserRole role) { 
        this.role = role; 
    }
}
