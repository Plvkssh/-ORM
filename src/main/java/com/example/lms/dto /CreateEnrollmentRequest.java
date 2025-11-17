package com.example.lms.dto;

import com.example.lms.model.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateEnrollmentRequest {
    @NotNull(message = "ID студента обязателен для заполнения")
    private Long studentId;
    
    @NotNull(message = "ID курса обязателен для заполнения")
    private Long courseId;
    
    private LocalDate enrollDate;
    private EnrollmentStatus status;

    // Конструкторы
    public CreateEnrollmentRequest() {}
    
    public CreateEnrollmentRequest(Long studentId, Long courseId, LocalDate enrollDate, EnrollmentStatus status) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollDate = enrollDate;
        this.status = status;
    }

    // Геттеры и сеттеры
    public Long getStudentId() { 
        return studentId; 
    }
    
    public void setStudentId(Long studentId) { 
        this.studentId = studentId; 
    }
    
    public Long getCourseId() { 
        return courseId; 
    }
    
    public void setCourseId(Long courseId) { 
        this.courseId = courseId; 
    }
    
    public LocalDate getEnrollDate() { 
        return enrollDate; 
    }
    
    public void setEnrollDate(LocalDate enrollDate) { 
        this.enrollDate = enrollDate; 
    }
    
    public EnrollmentStatus getStatus() { 
        return status; 
    }
    
    public void setStatus(EnrollmentStatus status) { 
        this.status = status; 
    }
}
