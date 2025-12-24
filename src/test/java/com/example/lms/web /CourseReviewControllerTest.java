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

    @BeforeEach
    void setupData() {
        User student = new User();
        student.setName("Тестовый студент");
        student.setEmail("student.review.test@example.com");
        student.setRole(UserRole.STUDENT);
        studentId = userRepository.save(student).getId();

        User teacher = new User();
        teacher.setName("Тестовый преподаватель");
        teacher.setEmail("teacher.review.test@example.com");
        teacher.setRole(UserRole.TEACHER);
        teacher = userRepository.save(teacher);

        Course course = new Course();
        course.setTitle("Курс для тестирования отзывов");
        course.setTeacher(teacher);
        courseId = courseRepository.save(course).getId();
    }

    @Test
    void createAndListReviews() throws Exception {
        String reviewRequestJson = String.format("""
            {
              "courseId": %d,
              "studentId": %d,
              "rating": 5,
              "comment": "Отличный курс! Преподаватель объясняет понятно, материалы полезные."
            }
            """, courseId, studentId);

        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reviewRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating", is(5)))
                .andExpect(jsonPath("$.courseId", is(courseId.intValue())));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk());
    }
}
