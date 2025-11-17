package com.example.lms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", unique = true, foreignKey = @ForeignKey(name = "fk_quiz_module"))
    private Module module;

    @Column(nullable = false)
    private String title;

    private Integer timeLimitMinutes;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizSubmission> quizSubmissions = new ArrayList<>();

    // Конструкторы
    public Quiz() {}
    
    public Quiz(Module module, String title, Integer timeLimitMinutes) {
        this.module = module;
        this.title = title;
        this.timeLimitMinutes = timeLimitMinutes;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Module getModule() { 
        return module; 
    }
    
    public void setModule(Module module) { 
        this.module = module; 
    }
    
    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    public Integer getTimeLimitMinutes() { 
        return timeLimitMinutes; 
    }
    
    public void setTimeLimitMinutes(Integer timeLimitMinutes) { 
        this.timeLimitMinutes = timeLimitMinutes; 
    }
    
    public List<Question> getQuestions() { 
        return questions; 
    }
    
    public void setQuestions(List<Question> questions) { 
        this.questions = questions; 
    }
    
    public List<QuizSubmission> getQuizSubmissions() { 
        return quizSubmissions; 
    }
    
    public void setQuizSubmissions(List<QuizSubmission> quizSubmissions) { 
        this.quizSubmissions = quizSubmissions; 
    }
}
