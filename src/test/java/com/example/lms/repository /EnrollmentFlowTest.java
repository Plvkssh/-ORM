package com.example.lms.repository;

import com.example.lms.model.*;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EnrollmentFlowTest extends PostgresContainerTest {

    @Autowired 
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired 
    private CourseRepository courseRepository;
    
    @Autowired 
    private UserRepository userRepository;

    @Test
    void enrollAndQueryByStudentAndCourse() {
        User student = new User();
        student.setName("Тестовый студент");
        student.setEmail("student.enrollment@example.com");
        student.setRole(UserRole.STUDENT);
        student = userRepository.save(student);

        User teacher = new User();
        teacher.setName("Тестовый преподаватель");
        teacher.setEmail("teacher.enrollment@example.com");
        teacher.setRole(UserRole.TEACHER);
        teacher = userRepository.save(teacher);

        Course course = new Course();
        course.setTitle("Тестовый курс для записи");
        course.setTeacher(teacher);
        course = courseRepository.save(course);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);

        List<Enrollment> enrollmentsByStudent = enrollmentRepository.findByStudent(student);
        List<Enrollment> enrollmentsByCourse = enrollmentRepository.findByCourse(course);

        assertThat(enrollmentsByStudent).hasSize(1);
        assertThat(enrollmentsByCourse).hasSize(1);
        assertThat(enrollmentsByStudent.get(0).getCourse().getId()).isEqualTo(course.getId());
    }
}
