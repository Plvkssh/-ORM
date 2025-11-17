package com.example.lms.dto;

import jakarta.validation.constraints.NotNull;

public class CreateQuizSubmissionRequest {
    @NotNull(message = "ID теста обязателен для заполнения")
    private Long quizId;
    
    @NotNull(message = "ID студента обязателен для заполнения")
    private Long studentId;
    
    private Integer score;

    // Конструкторы
    public CreateQuizSubmissionRequest() {}
    
    public CreateQuizSubmissionRequest(Long quizId, Long studentId, Integer score) {
        this.quizId = quizId;
        this.studentId = studentId;
        this.score = score;
    }

    // Геттеры и сеттеры
    public Long getQuizId() { 
        return quizId; 
    }
    
    public void setQuizId(Long quizId) { 
        this.quizId = quizId; 
    }
    
    public Long getStudentId() { 
        return studentId; 
    }
    
    public void setStudentId(Long studentId) { 
        this.studentId = studentId; 
    }
    
    public Integer getScore() { 
        return score; 
    }
    
    public void setScore(Integer score) { 
        this.score = score; 
    }
}
