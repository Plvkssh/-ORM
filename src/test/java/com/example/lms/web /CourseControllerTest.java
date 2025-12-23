package com.example.lms.web;

import com.example.lms.model.User;
import com.example.lms.model.UserRole;
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
 * Интеграционный тест для контроллера курсов (CourseController).
 * Проверяет работу REST endpoints для создания и получения курсов через HTTP запросы.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest extends PostgresContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private Long teacherId;

    /**
     * Настройка тестового окружения перед каждым тестом.
     * Создаёт преподавателя, который будет использоваться в тестах создания курсов.
     */
    @BeforeEach
    void setupTeacher() {
        User teacher = new User();
        teacher.setName("Тестовый преподаватель");
        teacher.setEmail("teacher.course.test@example.com");
        teacher.setRole(UserRole.TEACHER);
        teacherId = userRepository.save(teacher).getId();
    }

    /**
     * Тест создания нового курса и последующего получения списка курсов.
     * Проверяет полный цикл работы контроллера:
     * 1. Создание курса через POST запрос
     * 2. Проверка корректности созданного курса
     * 3. Получение списка всех курсов через GET запрос
     * 
     * @throws Exception если происходит ошибка в MockMvc
     */
    @Test
    void createAndGetCourse() throws Exception {
        // Подготовка JSON тела запроса для создания курса
        String courseRequestJson = String.format("""
            {
              "title": "Введение в Java программирование",
              "description": "Основы языка Java и объектно-ориентированного программирования",
              "teacherId": %d
            }
            """, teacherId);

        // Создание курса и проверка ответа
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(courseRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Введение в Java программирование")))
                .andExpect(jsonPath("$.teacherId", is(teacherId.intValue())));

        // Получение списка всех курсов
        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk());
    }
}
