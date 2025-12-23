package com.example.lms.repository;

import com.example.lms.model.*;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест каскадных операций для сущности Course.
 * Проверяет правильность работы каскадного сохранения и удаления связанных сущностей.
 */
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

    /**
     * Проверяет работу каскадных операций при сохранении и удалении курса.
     * Тест создаёт цепочку зависимостей: Пользователь → Курс → Модуль → Урок
     * и проверяет, что при удалении курса каскадно удаляются все связанные сущности.
     */
    @Test
    void persistCourseWithModuleAndLesson_cascadeWorks_andDeleteCascades() {
        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Тестовый преподаватель");
        teacher.setEmail("teacher.cascade@example.com");
        teacher.setRole(UserRole.TEACHER);
        teacher = userRepository.save(teacher);

        // Создание курса
        Course course = new Course();
        course.setTitle("Тестовый курс для проверки каскадов");
        course.setTeacher(teacher);
        course = courseRepository.save(course);

        // Создание модуля курса
        Module module = new Module();
        module.setCourse(course);
        module.setTitle("Тестовый модуль");
        module.setOrderIndex(1);
        module = moduleRepository.save(module);

        // Создание урока в модуле
        Lesson lesson = new Lesson();
        lesson.setModule(module);
        lesson.setTitle("Тестовый урок");
        lesson = lessonRepository.save(lesson);

        Long moduleId = module.getId();
        Long lessonId = lesson.getId();

        // Проверка, что все сущности сохранились
        assertThat(courseRepository.findById(course.getId())).isPresent();
        assertThat(moduleRepository.findById(moduleId)).isPresent();
        assertThat(lessonRepository.findById(lessonId)).isPresent();

        // Удаление курса
        courseRepository.deleteById(course.getId());

        // Проверка, что все связанные сущности удалены каскадно
        assertThat(courseRepository.findById(course.getId())).isNotPresent();
        assertThat(moduleRepository.findById(moduleId)).isNotPresent();
        assertThat(lessonRepository.findById(lessonId)).isNotPresent();
    }
}
