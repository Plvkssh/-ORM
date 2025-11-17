package com.example.lms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions", uniqueConstraints = {
        @UniqueConstraint(name = "uk_submission_student_assignment", columnNames = {"student_id", "assignment_id"})
})
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false, foreignKey = @ForeignKey(name = "fk_submission_assignment"))
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, foreignKey = @ForeignKey(name = "fk_submission_user"))
    private User student;

    @Column(nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    @Column(length = 10000)
    private String content;

    private Integer score;

    @Column(length = 2000)
    private String feedback;

    // Конструкторы
    public Submission() {}
    
    public Submission(Assignment assignment, User student, String content) {
        this.assignment = assignment;
        this.student = student;
        this.content = content;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Assignment getAssignment() { 
        return assignment; 
    }
    
    public void setAssignment(Assignment assignment) { 
        this.assignment = assignment; 
    }
    
    public User getStudent() { 
        return student; 
    }
    
    public void setStudent(User student) { 
        this.student = student; 
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
