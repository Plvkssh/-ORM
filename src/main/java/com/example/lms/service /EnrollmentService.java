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

/**
 * Сервис для управления записями студентов на курсы.
 * Обрабатывает регистрацию студентов, проверку дубликатов и управление статусами записей.
 */
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

    /**
     * Получает все записи на курсы из системы.
     *
     * @return список всех записей студентов на курсы
     */
    public List<Enrollment> findAll() { 
        return enrollmentRepository.findAll(); 
    }

    /**
     * Находит запись на курс по её идентификатору.
     *
     * @param id идентификатор записи
     * @return найденная запись
     * @throws NoSuchElementException если запись с указанным ID не существует
     */
    public Enrollment getById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Enrollment not found"));
    }

    /**
     * Создаёт новую запись на курс.
     *
     * @param enrollment запись для создания
     * @return сохранённая запись с присвоенным ID
     */
    public Enrollment create(Enrollment enrollment) { 
        return enrollmentRepository.save(enrollment); 
    }

    /**
     * Записывает студента на указанный курс с проверкой дублирования.
     *
     * @param courseId идентификатор курса
     * @param studentId идентификатор студента
     * @return созданная запись на курс
     * @throws NoSuchElementException если курс или студент не найдены
     * @throws IllegalStateException если студент уже записан на этот курс
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
     * Получает все записи указанного студента на различные курсы.
     *
     * @param studentId идентификатор студента
     * @return список записей студента на курсы
     * @throws NoSuchElementException если студент не найден
     */
    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
        return enrollmentRepository.findByStudent(student);
    }

    /**
     * Получает всех студентов, записанных на указанный курс.
     *
     * @param courseId идентификатор курса
     * @return список записей на данный курс
     * @throws NoSuchElementException если курс не найден
     */
    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));
        return enrollmentRepository.findByCourse(course);
    }

    /**
     * Обновляет существующую запись на курс.
     *
     * @param id идентификатор обновляемой записи
     * @param updated обновлённые данные записи
     * @return сохранённая обновлённая запись
     * @throws NoSuchElementException если запись с указанным ID не существует
     */
    public Enrollment update(Long id, Enrollment updated) {
        Enrollment existingEnrollment = getById(id);
        existingEnrollment.setStudent(updated.getStudent());
        existingEnrollment.setCourse(updated.getCourse());
        existingEnrollment.setEnrollDate(updated.getEnrollDate());
        existingEnrollment.setStatus(updated.getStatus());
        return enrollmentRepository.save(existingEnrollment);
    }

    /**
     * Удаляет запись на курс из системы.
     *
     * @param id идентификатор удаляемой записи
     */
    public void delete(Long id) { 
        enrollmentRepository.deleteById(id); 
    }
}
