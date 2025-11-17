package com.example.lms.dto;

import java.time.LocalDateTime;

public class SubmissionResponse {
    private Long id;
    private Long assignmentId;
    private Long studentId;
    private LocalDateTime submittedAt;
    private String content;
    private Integer score;
    private String feedback;

    // Конструкторы
    public SubmissionResponse() {}
    
    public SubmissionResponse(Long id, Long assignmentId, Long studentId, LocalDateTime submittedAt,
                            String content, Integer score, String feedback) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submittedAt = submittedAt;
        this.content = content;
        this.score = score;
        this.feedback = feedback;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
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
    
    public LocalDateTime getSubmittedAt() { 
        return submittedAt; 
    }
    
    public void setSubmittedAt(LocalDateTime submittedAt) { 
        this.submittedAt = submittedAt; 
    }
    
    public String getContent() { 
        return content; 
    }
    
    public void setContent(String content) { 
        this.content = content; 
    }
    
    public Integer getScore() { 
        return score; 
    }
    
    public void setScore(Integer score) { 
        this.score = score; 
    }
    
    public String getFeedback() { 
        return feedback; 
    }
    
    public void setFeedback(String feedback) { 
        this.feedback = feedback; 
    }
}
