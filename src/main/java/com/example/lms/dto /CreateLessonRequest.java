package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateLessonRequest {
    @NotNull(message = "ID модуля обязателен для заполнения")
    private Long moduleId;
    
    @NotBlank(message = "Название урока не может быть пустым")
    private String title;
    
    private String content;
    private String videoUrl;

    // Конструкторы
    public CreateLessonRequest() {}
    
    public CreateLessonRequest(Long moduleId, String title, String content, String videoUrl) {
        this.moduleId = moduleId;
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
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
    
    public String getContent() { 
        return content; 
    }
    
    public void setContent(String content) { 
        this.content = content; 
    }
    
    public String getVideoUrl() { 
        return videoUrl; 
    }
    
    public void setVideoUrl(String videoUrl) { 
        this.videoUrl = videoUrl; 
    }
}
