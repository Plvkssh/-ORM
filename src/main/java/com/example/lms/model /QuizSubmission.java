package com.example.lms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_submissions")
public class QuizSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false, foreignKey = @ForeignKey(name = "fk_quiz_submission_quiz"))
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, foreignKey = @ForeignKey(name = "fk_quiz_submission_user"))
    private User student;

    private Integer score;

    @Column(nullable = false)
    private LocalDateTime takenAt = LocalDateTime.now();

    // Конструкторы
    public QuizSubmission() {}
    
    public QuizSubmission(Quiz quiz, User student, Integer score) {
        this.quiz = quiz;
        this.student = student;
        this.score = score;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Quiz getQuiz() { 
        return quiz; 
    }
    
    public void setQuiz(Quiz quiz) { 
        this.quiz = quiz; 
    }
    
    public User getStudent() { 
        return student; 
    }
    
    public void setStudent(User student) { 
        this.student = student; 
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
}
