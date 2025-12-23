package com.example.lms.web;

import com.example.lms.model.*;
import com.example.lms.repository.ModuleRepository;
import com.example.lms.repository.QuizRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Интеграционный тест для контроллера вопросов тестов (QuestionController).
 * Проверяет работу REST endpoints для создания вопросов тестов и связанных операций.
 */
@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest extends PostgresContainerTest {

    @Autowired 
    private MockMvc mockMvc;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired 
    private ModuleRepository moduleRepository;
    
    @Autowired 
    private QuizRepository quizRepository;

    private Long quizId;

    /**
     * Настройка тестового окружения перед каждым тестом.
     * Создаёт цепочку зависимостей: Преподаватель → Курс → Модуль → Тест
     * для использования в тестах вопросов.
     */
    @BeforeEach
    void setup() {
        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Тестовый преподаватель");
        teacher.setEmail("teacher.question.test@example.com");
        teacher.setRole(UserRole.TEACHER);
        teacher = userRepository.save(teacher);

        // Создание курса
        Course course = new Course();
        course.setTitle("Курс для тестирования вопросов");
        course.setTeacher(teacher);

        // Создание модуля
        Module module = new Module();
        module.setCourse(course);
        module.setTitle("Модуль для тестирования");
        module.setOrderIndex(1);
        course.getModules().add(module);
        moduleRepository.save(module);

        // Создание теста
        Quiz quiz = new Quiz();
        quiz.setModule(module);
        quiz.setTitle("Тест по основам программирования");
        quizId = quizRepository.save(quiz).getId();
    }

    /**
     * Тест создания вопроса теста и получения списка вопросов.
     * Проверяет работу контроллера вопросов:
     * 1. Создание вопроса через POST запрос
     * 2. Проверка корректности созданного вопроса
     * 3. Получение списка всех вопросов через GET запрос
     * 
     * @throws Exception если происходит ошибка в MockMvc
     */
    @Test
    void createQuestionAndAddOption() throws Exception {
        // Подготовка JSON тела запроса для создания вопроса
        String questionRequestJson = String.format("""
            {
              "quizId": %d,
              "text": "Какая сложность у алгоритма быстрой сортировки в среднем случае?",
              "type": "SINGLE_CHOICE"
            }
            """, quizId);

        // Создание вопроса и проверка ответа
        String response = mockMvc.perform(post("/api/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(questionRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text", is("Какая сложность у алгоритма быстрой сортировки в среднем случае?")))
                .andReturn().getResponse().getContentAsString();

        // Получение списка всех вопросов
        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk());
    }
}
