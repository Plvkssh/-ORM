package com.example.lms.dto;

import java.time.LocalDateTime;

public class AssignmentResponse {
    private Long id;
    private Long lessonId;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Integer maxScore;

    public AssignmentResponse() {}
    
    public AssignmentResponse(Long id, Long lessonId, String title, String description, 
                        LocalDateTime dueDate, Integer maxScore) {
        this.id = id;
        this.lessonId = lessonId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.maxScore = maxScore;
    }

    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
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
    
    @Override
    public String toString() {
        return "AssignmentResponse{id=" + id + 
               ", lessonId=" + lessonId + 
               ", title='" + title + '\'' + 
               ", description='" + description + '\'' + 
               ", dueDate=" + dueDate + 
               ", maxScore=" + maxScore + '}';
    }
}
