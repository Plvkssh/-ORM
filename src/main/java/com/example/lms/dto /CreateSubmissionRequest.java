package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateSubmissionRequest {
    @NotNull(message = "ID задания обязателен для заполнения")
    private Long assignmentId;
    
    @NotNull(message = "ID студента обязателен для заполнения")
    private Long studentId;
    
    @NotBlank(message = "Содержание решения не может быть пустым")
    private String content;

    // Конструкторы
    public CreateSubmissionRequest() {}
    
    public CreateSubmissionRequest(Long assignmentId, Long studentId, String content) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.content = content;
    }

    // Геттеры и сеттеры
    public Long getAssignmentId() { 
        return assignmentId; 
    }
    
    public void setAssignmentId(Long assignmentId) { 
        this.assignmentId = assignmentId; 
    }
    
    public Long getStudentId() { 
        return studentId; 
    }
    
    public void setStudentId(Long studentId) { 
        this.studentId = studentId; 
    }
    
    public String getContent() { 
        return content; 
    }
    
    public void setContent(String content) { 
        this.content = content; 
    }
}
