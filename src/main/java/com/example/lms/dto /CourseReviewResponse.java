package com.example.lms.dto;

import java.time.LocalDateTime;

public class CourseReviewResponse {
    private Long id;
    private Long courseId;
    private Long studentId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    // Конструкторы
    public CourseReviewResponse() {}
    
    public CourseReviewResponse(Long id, Long courseId, Long studentId, Integer rating, 
                               String comment, LocalDateTime createdAt) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Long getCourseId() { 
        return courseId; 
    }
    
    public void setCourseId(Long courseId) { 
        this.courseId = courseId; 
    }
    
    public Long getStudentId() { 
        return studentId; 
    }
    
    public void setStudentId(Long studentId) { 
        this.studentId = studentId; 
    }
    
    public Integer getRating() { 
        return rating; 
    }
    
    public void setRating(Integer rating) { 
        this.rating = rating; 
    }
    
    public String getComment() { 
        return comment; 
    }
    
    public void setComment(String comment) { 
        this.comment = comment; 
    }
    
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
}
