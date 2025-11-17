package com.example.lms.dto;

import com.example.lms.model.EnrollmentStatus;
import java.time.LocalDate;

public class EnrollmentResponse {
    private Long id;
    private Long studentId;
    private Long courseId;
    private LocalDate enrollDate;
    private EnrollmentStatus status;

    // Конструкторы
    public EnrollmentResponse() {}
    
    public EnrollmentResponse(Long id, Long studentId, Long courseId, 
                             LocalDate enrollDate, EnrollmentStatus status) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollDate = enrollDate;
        this.status = status;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
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
