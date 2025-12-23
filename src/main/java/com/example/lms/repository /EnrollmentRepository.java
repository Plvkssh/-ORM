package com.example.lms.repository;

import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления записями студентов на курсы.
 * Обрабатывает регистрацию студентов и их статусы в курсах.
 */
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    /**
     * Находит все записи указанного студента на различные курсы.
     * Используется для отображения списка курсов, на которые записан студент.
     *
     * @param student студент, чьи записи нужно найти
     * @return список записей студента на курсы
     */
    List<Enrollment> findByStudent(User student);
    
    /**
     * Находит всех студентов, записанных на указанный курс.
     * Полезно для управления составом участников курса.
     *
     * @param course курс, для которого нужно найти записи
     * @return список записей на данный курс
     */
    List<Enrollment> findByCourse(Course course);
    
    /**
     * Проверяет, записан ли конкретный студент на конкретный курс.
     * Используется для предотвращения повторной записи.
     *
     * @param student студент для проверки
     * @param course курс для проверки
     * @return Optional с записью, если студент записан на курс
     */
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);
}
