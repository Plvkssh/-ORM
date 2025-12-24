package com.example.lms.repository;

import com.example.lms.model.*;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CourseCascadeTest extends PostgresContainerTest {

    @Autowired 
    private CourseRepository courseRepository;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired 
    private ModuleRepository moduleRepository;
    
    @Autowired 
    private LessonRepository lessonRepository;

    @Test
    void persistCourseWithModuleAndLesson_cascadeWorks_andDeleteCascades() {
        User teacher = new User();
        teacher.setName("Тестовый преподаватель");
        teacher.setEmail("teacher.cascade@example.com");
        teacher.setRole(UserRole.TEACHER);
        teacher = userRepository.save(teacher);

        Course course = new Course();
        course.setTitle("Тестовый курс для проверки каскадов");
        course.setTeacher(teacher);
        course = courseRepository.save(course);

        Module module = new Module();
        module.setCourse(course);
        module.setTitle("Тестовый модуль");
        module.setOrderIndex(1);
        module = moduleRepository.save(module);

        Lesson lesson = new Lesson();
        lesson.setModule(module);
        lesson.setTitle("Тестовый урок");
        lesson = lessonRepository.save(lesson);

        Long moduleId = module.getId();
        Long lessonId = lesson.getId();

        assertThat(courseRepository.findById(course.getId())).isPresent();
        assertThat(moduleRepository.findById(moduleId)).isPresent();
        assertThat(lessonRepository.findById(lessonId)).isPresent();

        courseRepository.deleteById(course.getId());

        assertThat(courseRepository.findById(course.getId())).isNotPresent();
        assertThat(moduleRepository.findById(moduleId)).isNotPresent();
        assertThat(lessonRepository.findById(lessonId)).isNotPresent();
    }
}
