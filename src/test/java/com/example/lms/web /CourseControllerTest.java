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

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest extends PostgresContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private Long teacherId;

    @BeforeEach
    void setupTeacher() {
        User teacher = new User();
        teacher.setName("Тестовый преподаватель");
        teacher.setEmail("teacher.course.test@example.com");
        teacher.setRole(UserRole.TEACHER);
        teacherId = userRepository.save(teacher).getId();
    }

    @Test
    void createAndGetCourse() throws Exception {
        String courseRequestJson = String.format("""
            {
              "title": "Введение в Java программирование",
              "description": "Основы языка Java и объектно-ориентированного программирования",
              "teacherId": %d
            }
            """, teacherId);

        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(courseRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Введение в Java программирование")))
                .andExpect(jsonPath("$.teacherId", is(teacherId.intValue())));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk());
    }
}
