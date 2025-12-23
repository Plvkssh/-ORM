package com.example.lms.web;

import com.example.lms.model.Course;
import com.example.lms.model.User;
import com.example.lms.model.UserRole;
import com.example.lms.repository.CourseRepository;
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
 * Интеграционный тест для контроллера отзывов на курсы (CourseReviewController).
 * Проверяет работу REST endpoints для создания и получения отзывов студентов о курсах.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CourseReviewControllerTest extends PostgresContainerTest {

    @Autowired 
    private MockMvc mockMvc;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired 
    private CourseRepository courseRepository;

    private Long studentId;
    private Long courseId;

    /**
     * Настройка тестового окружения перед каждым тестом.
     * Создаёт студента, преподавателя и курс для использования в тестах отзывов.
     */
    @BeforeEach
    void setupData() {
        // Создание студента
        User student = new User();
        student.setName("Тестовый студент");
        student.setEmail("student.review.test@example.com");
        student.setRole(UserRole.STUDENT);
        studentId = userRepository.save(student).getId();

        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Тестовый преподаватель");
        teacher.setEmail("teacher.review.test@example.com");
        teacher.setRole(UserRole.TEACHER);
        teacher = userRepository.save(teacher);

        // Создание курса
        Course course = new Course();
        course.setTitle("Курс для тестирования отзывов");
        course.setTeacher(teacher);
        courseId = courseRepository.save(course).getId();
    }

    /**
     * Тест создания отзыва на курс и получения списка всех отзывов.
     * Проверяет полный цикл работы контроллера отзывов:
     * 1. Создание отзыва через POST запрос
     * 2. Проверка корректности созданного отзыва
     * 3. Получение списка всех отзывов через GET запрос
     * 
     * @throws Exception если происходит ошибка в MockMvc
     */
    @Test
    void createAndListReviews() throws Exception {
        // Подготовка JSON тела запроса для создания отзыва
        String reviewRequestJson = String.format("""
            {
              "courseId": %d,
              "studentId": %d,
              "rating": 5,
              "comment": "Отличный курс! Преподаватель объясняет понятно, материалы полезные."
            }
            """, courseId, studentId);

        // Создание отзыва и проверка ответа
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reviewRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating", is(5)))
                .andExpect(jsonPath("$.courseId", is(courseId.intValue())));

        // Получение списка всех отзывов
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk());
    }
}
