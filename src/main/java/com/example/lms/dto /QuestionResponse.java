package com.example.lms.dto;

import com.example.lms.model.QuestionType;
import java.util.List;

public class QuestionResponse {
    private Long id;
    private Long quizId;
    private String text;
    private QuestionType type;
    private List<AnswerOptionResponse> options;

    // Конструкторы
    public QuestionResponse() {}
    
    public QuestionResponse(Long id, Long quizId, String text, QuestionType type, List<AnswerOptionResponse> options) {
        this.id = id;
        this.quizId = quizId;
        this.text = text;
        this.type = type;
        this.options = options;
    }

    // Геттеры и сеттеры
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
    
    public List<AnswerOptionResponse> getOptions() { 
        return options; 
    }
    
    public void setOptions(List<AnswerOptionResponse> options) { 
        this.options = options; 
    }
}
