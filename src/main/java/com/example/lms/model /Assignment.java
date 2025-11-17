package com.example.lms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false, foreignKey = @ForeignKey(name = "fk_assignment_lesson"))
    private Lesson lesson;

    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String description;

    private LocalDateTime dueDate;

    @Column(nullable = false)
    private Integer maxScore = 100;

    @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Submission> submissions = new ArrayList<>();

    // Конструкторы
    public Assignment() {}
    
    public Assignment(Lesson lesson, String title, String description, LocalDateTime dueDate, Integer maxScore) {
        this.lesson = lesson;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.maxScore = maxScore;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Lesson getLesson() { 
        return lesson; 
    }
    
    public void setLesson(Lesson lesson) { 
        this.lesson = lesson; 
    }
    
    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public LocalDateTime getDueDate() { 
        return dueDate; 
    }
    
    public void setDueDate(LocalDateTime dueDate) { 
        this.dueDate = dueDate; 
    }
    
    public Integer getMaxScore() { 
        return maxScore; 
    }
    
    public void setMaxScore(Integer maxScore) { 
        this.maxScore = maxScore; 
    }
    
    public List<Submission> getSubmissions() { 
        return submissions; 
    }
    
    public void setSubmissions(List<Submission> submissions) { 
        this.submissions = submissions; 
    }
}
