package com.example.lms.repository;

import com.example.lms.model.*;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест для проверки обновления оценок и обратной связи для решений заданий.
 * Проверяет корректность сохранения и обновления оценок в сущности Submission.
 */
@DataJpaTest
class SubmissionUpdateTest extends PostgresContainerTest {

    @Autowired 
    private UserRepository userRepository;
    
    @Autowired 
    private CourseRepository courseRepository;
    
    @Autowired 
    private ModuleRepository moduleRepository;
    
    @Autowired 
    private LessonRepository lessonRepository;
    
    @Autowired 
    private AssignmentRepository assignmentRepository;
    
    @Autowired 
    private SubmissionRepository submissionRepository;

    /**
     * Проверяет процесс создания и обновления решения задания с оценкой и обратной связью.
     * Тест создаёт цепочку зависимостей: Студент → Преподаватель → Курс → Модуль → Урок → Задание → Решение,
     * затем обновляет оценку и обратную связь, и проверяет корректность сохранения изменений.
     */
    @Test
    void updateSubmissionScore() {
        // Создание студента
        User student = new User();
        student.setName("Тестовый студент");
        student.setEmail("student.submission@example.com");
        student.setRole(UserRole.STUDENT);
        student = userRepository.save(student);

        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Тестовый преподаватель");
        teacher.setEmail("teacher.submission@example.com");
        teacher.setRole(UserRole.TEACHER);
        teacher = userRepository.save(teacher);

        // Создание курса
        Course course = new Course();
        course.setTitle("Тестовый курс для заданий");
        course.setTeacher(teacher);
        course = courseRepository.save(course);

        // Создание модуля
        Module module = new Module();
        module.setCourse(course);
        module.setTitle("Тестовый модуль");
        module.setOrderIndex(1);
        module = moduleRepository.save(module);

        // Создание урока
        Lesson lesson = new Lesson();
        lesson.setModule(module);
        lesson.setTitle("Тестовый урок");
        lesson = lessonRepository.save(lesson);

        // Создание задания
        Assignment assignment = new Assignment();
        assignment.setLesson(lesson);
        assignment.setTitle("Тестовое задание");
        assignment = assignmentRepository.save(assignment);

        // Создание решения студента
        Submission submission = new Submission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setContent("Ответ студента на задание");
        submission = submissionRepository.save(submission);

        // Обновление оценки и обратной связи
        submission.setScore(90);
        submission.setFeedback("Хорошая работа, но можно улучшить оформление");
        submissionRepository.save(submission);

        // Проверка сохранённых изменений
        Submission reloadedSubmission = submissionRepository.findById(submission.getId()).orElseThrow();
        assertThat(reloadedSubmission.getScore()).isEqualTo(90);
        assertThat(reloadedSubmission.getFeedback()).isEqualTo("Хорошая работа, но можно улучшить оформление");
    }
}
