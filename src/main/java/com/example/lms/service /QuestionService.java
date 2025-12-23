package com.example.lms.service;

import com.example.lms.model.AnswerOption;
import com.example.lms.model.Question;
import com.example.lms.repository.AnswerOptionRepository;
import com.example.lms.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис для управления вопросами тестов и их вариантами ответов.
 * Обеспечивает операции с вопросами и связанными с ними вариантами ответов.
 */
@Service
@Transactional
public class QuestionService {
    
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository) {
        this.questionRepository = questionRepository;
        this.answerOptionRepository = answerOptionRepository;
    }

    /**
     * Получает все вопросы из системы.
     *
     * @return список всех вопросов тестов
     */
    public List<Question> findAll() { 
        return questionRepository.findAll(); 
    }

    /**
     * Находит вопрос по его идентификатору.
     *
     * @param id идентификатор вопроса
     * @return найденный вопрос
     * @throws NoSuchElementException если вопрос с указанным ID не существует
     */
    public Question getById(Long id) { 
        return questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found")); 
    }

    /**
     * Создаёт новый вопрос теста.
     *
     * @param question вопрос для создания
     * @return сохранённый вопрос с присвоенным ID
     */
    public Question create(Question question) { 
        return questionRepository.save(question); 
    }

    /**
     * Обновляет существующий вопрос теста.
     *
     * @param id идентификатор обновляемого вопроса
     * @param updated обновлённые данные вопроса
     * @return сохранённый обновлённый вопрос
     * @throws NoSuchElementException если вопрос с указанным ID не существует
     */
    public Question update(Long id, Question updated) {
        Question existingQuestion = getById(id);
        existingQuestion.setQuiz(updated.getQuiz());
        existingQuestion.setText(updated.getText());
        existingQuestion.setType(updated.getType());
        return questionRepository.save(existingQuestion);
    }

    /**
     * Удаляет вопрос теста из системы.
     *
     * @param id идентификатор удаляемого вопроса
     */
    public void delete(Long id) { 
        questionRepository.deleteById(id); 
    }

    /**
     * Добавляет вариант ответа к вопросу.
     *
     * @param option вариант ответа для добавления
     * @return сохранённый вариант ответа с присвоенным ID
     */
    public AnswerOption addOption(AnswerOption option) { 
        return answerOptionRepository.save(option); 
    }

    /**
     * Удаляет вариант ответа из системы.
     *
     * @param optionId идентификатор удаляемого варианта ответа
     */
    public void deleteOption(Long optionId) { 
        answerOptionRepository.deleteById(optionId); 
    }
}
