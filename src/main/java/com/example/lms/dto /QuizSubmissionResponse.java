package com.example.lms.dto;

import java.time.LocalDateTime;

public class QuizSubmissionResponse {
    private Long id;
    private Long quizId;
    private Long studentId;
    private Integer score;
    private LocalDateTime takenAt;

    public QuizSubmissionResponse() {}
    
    public QuizSubmissionResponse(Long id, Long quizId, Long studentId, Integer score, LocalDateTime takenAt) {
        this.id = id;
        this.quizId = quizId;
        this.studentId = studentId;
        this.score = score;
        this.takenAt = takenAt;
    }

    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
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
    
    public LocalDateTime getTakenAt() { 
        return takenAt; 
    }
    
    public void setTakenAt(LocalDateTime takenAt) { 
        this.takenAt = takenAt; 
    }
    
    @Override
    public String toString() {
        return "QuizSubmissionResponse{id=" + id + 
               ", quizId=" + quizId + 
               ", studentId=" + studentId + 
               ", score=" + score + 
               ", takenAt=" + takenAt + '}';
    }
}
