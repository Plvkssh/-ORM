package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreateAssignmentRequest {
    @NotNull(message = "ID урока обязателен для заполнения")
    private Long lessonId;
    
    @NotBlank(message = "Название задания не может быть пустым")
    private String title;
    
    private String description;
    private LocalDateTime dueDate;
    
    @NotNull(message = "Максимальный балл должен быть указан")
    private Integer maxScore;

    // Конструкторы
    public CreateAssignmentRequest() {}
    
    public CreateAssignmentRequest(Long lessonId, String title, String description, 
                                 LocalDateTime dueDate, Integer maxScore) {
        this.lessonId = lessonId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.maxScore = maxScore;
    }

    // Геттеры и сеттеры
    public Long getLessonId() { 
        return lessonId; 
    }
    
    public void setLessonId(Long lessonId) { 
        this.lessonId = lessonId; 
    }
    
    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public LocalDateTime getDueDate() { 
        return dueDate; 
    }
    
    public void setDueDate(LocalDateTime dueDate) { 
        this.dueDate = dueDate; 
    }
    
    public Integer getMaxScore() { 
        return maxScore; 
    }
    
    public void setMaxScore(Integer maxScore) { 
        this.maxScore = maxScore; 
    }
}
