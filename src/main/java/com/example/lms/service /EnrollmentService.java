package com.example.lms.service;

import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.User;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.EnrollmentRepository;
import com.example.lms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, UserRepository userRepository, 
                           CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public List<Enrollment> findAll() { 
        return enrollmentRepository.findAll(); 
    }

    /**
     * Возвращает запись на курс по ID. Если запись не найдена, выбрасывает исключение.
     */
    public Enrollment getById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Enrollment not found"));
    }

    public Enrollment create(Enrollment enrollment) { 
        return enrollmentRepository.save(enrollment); 
    }

    /**
     * Записывает студента на курс с проверкой дублирования.
     * Проверяет существование курса и студента перед записью.
     */
    public Enrollment enrollStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
        
        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByStudentAndCourse(student, course);
        if (existingEnrollment.isPresent()) {
            throw new IllegalStateException("Student is already enrolled in this course");
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }

    /**
     * Получает все записи указанного студента на курсы.
     * Проверяет существование студента перед поиском записей.
     */
    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
        return enrollmentRepository.findByStudent(student);
    }

    /**
     * Получает всех студентов, записанных на указанный курс.
     * Проверяет существование курса перед поиском записей.
     */
    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));
        return enrollmentRepository.findByCourse(course);
    }

    /**
     * Обновляет существующую запись на курс. Сначала проверяет её существование.
     */
    public Enrollment update(Long id, Enrollment updatedEnrollment) {
        Enrollment existingEnrollment = getById(id);
        existingEnrollment.setStudent(updatedEnrollment.getStudent());
        existingEnrollment.setCourse(updatedEnrollment.getCourse());
        existingEnrollment.setEnrollDate(updatedEnrollment.getEnrollDate());
        existingEnrollment.setStatus(updatedEnrollment.getStatus());
        return enrollmentRepository.save(existingEnrollment);
    }

    public void delete(Long id) { 
        enrollmentRepository.deleteById(id); 
    }
}
