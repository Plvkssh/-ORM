package com.example.lms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false, foreignKey = @ForeignKey(name = "fk_question_quiz"))
    private Quiz quiz;

    @Column(nullable = false, length = 2000)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private QuestionType type;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOption> options = new ArrayList<>();

    // Конструкторы
    public Question() {}
    
    public Question(Quiz quiz, String text, QuestionType type) {
        this.quiz = quiz;
        this.text = text;
        this.type = type;
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
    
    public String getText() { 
        return text; 
    }
    
    public void setText(String text) { 
        this.text = text; 
    }
    
    public QuestionType getType() { 
        return type; 
    }
    
    public void setType(QuestionType type) { 
        this.type = type; 
    }
    
    public List<AnswerOption> getOptions() { 
        return options; 
    }
    
    public void setOptions(List<AnswerOption> options) { 
        this.options = options; 
    }
}
