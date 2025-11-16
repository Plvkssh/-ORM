package com.example.lms.dto;

public class AnswerOptionDto {
    private Long id;
    private Long questionId;
    private String text;
    private boolean correct;

    // Конструкторы
    public AnswerOptionDto() {}
    
    public AnswerOptionDto(Long id, Long questionId, String text, boolean correct) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.correct = correct;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Long getQuestionId() { 
        return questionId; 
    }
    
    public void setQuestionId(Long questionId) { 
        this.questionId = questionId; 
    }
    
    public String getText() { 
        return text; 
    }
    
    public void setText(String text) { 
        this.text = text; 
    }
    
    public boolean isCorrect() { 
        return correct; 
    }
    
    public void setCorrect(boolean correct) { 
        this.correct = correct; 
    }
}
