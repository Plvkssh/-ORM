package com.example.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateCourseReviewRequest {
    @NotNull(message = "ID курса обязателен для заполнения")
    private Long courseId;
    
    @NotNull(message = "ID студента обязателен для заполнения")
    private Long studentId;
    
    @NotNull(message = "Рейтинг обязателен для заполнения")
    @Min(value = 1, message = "Рейтинг должен быть не менее 1")
    @Max(value = 5, message = "Рейтинг должен быть не более 5")
    private Integer rating;
    
    private String comment;

    // Конструкторы
    public CreateCourseReviewRequest() {}
    
    public CreateCourseReviewRequest(Long courseId, Long studentId, Integer rating, String comment) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.rating = rating;
        this.comment = comment;
    }

    // Геттеры и сеттеры
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
}
