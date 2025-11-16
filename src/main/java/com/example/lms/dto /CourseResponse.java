package com.example.lms.dto;

import java.time.LocalDate;
import java.util.Set;

public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private String duration;
    private LocalDate startDate;
    private Long categoryId;
    private Long teacherId;
    private Set<Long> tagIds;

    // Конструкторы
    public CourseResponse() {}
    
    public CourseResponse(Long id, String title, String description, String duration, 
                         LocalDate startDate, Long categoryId, Long teacherId, Set<Long> tagIds) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.startDate = startDate;
        this.categoryId = categoryId;
        this.teacherId = teacherId;
        this.tagIds = tagIds;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
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
    
    public String getDuration() { 
        return duration; 
    }
    
    public void setDuration(String duration) { 
        this.duration = duration; 
    }
    
    public LocalDate getStartDate() { 
        return startDate; 
    }
    
    public void setStartDate(LocalDate startDate) { 
        this.startDate = startDate; 
    }
    
    public Long getCategoryId() { 
        return categoryId; 
    }
    
    public void setCategoryId(Long categoryId) { 
        this.categoryId = categoryId; 
    }
    
    public Long getTeacherId() { 
        return teacherId; 
    }
    
    public void setTeacherId(Long teacherId) { 
        this.teacherId = teacherId; 
    }
    
    public Set<Long> getTagIds() { 
        return tagIds; 
    }
    
    public void setTagIds(Set<Long> tagIds) { 
        this.tagIds = tagIds; 
    }
}
