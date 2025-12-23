package com.example.lms.repository;

import com.example.lms.model.*;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест для проверки работы записей студентов на курсы.
 * Проверяет корректность регистрации студентов и запросов к репозиторию Enrollment.
 */
@DataJpaTest
class EnrollmentFlowTest extends PostgresContainerTest {

    @Autowired 
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired 
    private CourseRepository courseRepository;
    
    @Autowired 
    private UserRepository userRepository;

    /**
     * Проверяет процесс записи студента на курс и корректность запросов
     * по студенту и курсу.
     * Тест создаёт студента, преподавателя, курс, записывает студента на курс
     * и проверяет, что запросы findByStudent и findByCourse возвращают правильные данные.
     */
    @Test
    void enrollAndQueryByStudentAndCourse() {
        // Создание студента
        User student = new User();
        student.setName("Тестовый студент");
        student.setEmail("student.enrollment@example.com");
        student.setRole(UserRole.STUDENT);
        student = userRepository.save(student);

        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Тестовый преподаватель");
        teacher.setEmail("teacher.enrollment@example.com");
        teacher.setRole(UserRole.TEACHER);
        teacher = userRepository.save(teacher);

        // Создание курса
        Course course = new Course();
        course.setTitle("Тестовый курс для записи");
        course.setTeacher(teacher);
        course = courseRepository.save(course);

        // Запись студента на курс
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);

        // Получение записей по студенту и курсу
        List<Enrollment> enrollmentsByStudent = enrollmentRepository.findByStudent(student);
        List<Enrollment> enrollmentsByCourse = enrollmentRepository.findByCourse(course);

        // Проверка результатов
        assertThat(enrollmentsByStudent).hasSize(1);
        assertThat(enrollmentsByCourse).hasSize(1);
        assertThat(enrollmentsByStudent.get(0).getCourse().getId()).isEqualTo(course.getId());
    }
}
