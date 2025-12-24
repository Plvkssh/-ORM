package com.example.lms.service;

import com.example.lms.model.AnswerOption;
import com.example.lms.model.Question;
import com.example.lms.repository.AnswerOptionRepository;
import com.example.lms.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class QuestionService {
    
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository) {
        this.questionRepository = questionRepository;
        this.answerOptionRepository = answerOptionRepository;
    }

    public List<Question> findAll() { 
        return questionRepository.findAll(); 
    }

    /**
     * Возвращает вопрос по ID. Если вопрос не найден, выбрасывает исключение.
     */
    public Question getById(Long id) { 
        return questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found")); 
    }

    public Question create(Question question) { 
        return questionRepository.save(question); 
    }

    /**
     * Обновляет существующий вопрос. Сначала проверяет его существование.
     */
    public Question update(Long id, Question updatedQuestion) {
        Question existingQuestion = getById(id);
        existingQuestion.setQuiz(updatedQuestion.getQuiz());
        existingQuestion.setText(updatedQuestion.getText());
        existingQuestion.setType(updatedQuestion.getType());
        return questionRepository.save(existingQuestion);
    }

    public void delete(Long id) { 
        questionRepository.deleteById(id); 
    }

    public AnswerOption addOption(AnswerOption option) { 
        return answerOptionRepository.save(option); 
    }

    public void deleteOption(Long optionId) { 
        answerOptionRepository.deleteById(optionId); 
    }
}
