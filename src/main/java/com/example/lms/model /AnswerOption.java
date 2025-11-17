package com.example.lms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "answer_options")
public class AnswerOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false, foreignKey = @ForeignKey(name = "fk_option_question"))
    private Question question;

    @Column(nullable = false, length = 1000)
    private String text;

    @Column(nullable = false)
    private boolean isCorrect;

    // Конструкторы
    public AnswerOption() {}
    
    public AnswerOption(Question question, String text, boolean isCorrect) {
        this.question = question;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Question getQuestion() { 
        return question; 
    }
    
    public void setQuestion(Question question) { 
        this.question = question; 
    }
    
    public String getText() { 
        return text; 
    }
    
    public void setText(String text) { 
        this.text = text; 
    }
    
    public boolean isCorrect() { 
        return isCorrect; 
    }
    
    public void setCorrect(boolean correct) { 
        isCorrect = correct; 
    }
}
