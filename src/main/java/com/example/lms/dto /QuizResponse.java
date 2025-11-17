package com.example.lms.dto;

public class QuizResponse {
    private Long id;
    private Long moduleId;
    private String title;
    private Integer timeLimitMinutes;

    // Конструкторы
    public QuizResponse() {}
    
    public QuizResponse(Long id, Long moduleId, String title, Integer timeLimitMinutes) {
        this.id = id;
        this.moduleId = moduleId;
        this.title = title;
        this.timeLimitMinutes = timeLimitMinutes;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
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
