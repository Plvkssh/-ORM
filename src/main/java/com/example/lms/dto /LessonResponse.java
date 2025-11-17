package com.example.lms.dto;

public class LessonResponse {
    private Long id;
    private Long moduleId;
    private String title;
    private String content;
    private String videoUrl;

    // Конструкторы
    public LessonResponse() {}
    
    public LessonResponse(Long id, Long moduleId, String title, String content, String videoUrl) {
        this.id = id;
        this.moduleId = moduleId;
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
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
