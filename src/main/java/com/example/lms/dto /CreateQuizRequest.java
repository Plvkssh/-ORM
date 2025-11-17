package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateQuizRequest {
    @NotNull(message = "ID модуля обязателен для заполнения")
    private Long moduleId;
    
    @NotBlank(message = "Название теста не может быть пустым")
    private String title;
    
    private Integer timeLimitMinutes;

    // Конструкторы
    public CreateQuizRequest() {}
    
    public CreateQuizRequest(Long moduleId, String title, Integer timeLimitMinutes) {
        this.moduleId = moduleId;
        this.title = title;
        this.timeLimitMinutes = timeLimitMinutes;
    }

    // Геттеры и сеттеры
    public Long getModuleId() { 
        return moduleId; 
    }
    
    public void setModuleId(Long moduleId) { 
        this.moduleId = moduleId; 
    }
    
    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    public Integer getTimeLimitMinutes() { 
        return timeLimitMinutes; 
    }
    
    public void setTimeLimitMinutes(Integer timeLimitMinutes) { 
        this.timeLimitMinutes = timeLimitMinutes; 
    }
}
