package com.example.lms.repository;

import com.example.lms.model.Course;
import com.example.lms.model.CourseReview;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для управления отзывами на курсы.
 * Обеспечивает доступ к отзывам студентов о пройденных курсах.
 */
public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {
    
    /**
     * Находит все отзывы для указанного курса.
     * Полезно для отображения всех оценок и комментариев курса.
     *
     * @param course курс, для которого нужно получить отзывы
     * @return список отзывов по курсу
     */
    List<CourseReview> findByCourse(Course course);
    
    /**
     * Находит все отзывы, оставленные конкретным студентом.
     * Используется для просмотра истории отзывов пользователя.
     *
     * @param student студент, чьи отзывы нужно найти
     * @return список отзывов студента
     */
    List<CourseReview> findByStudent(User student);
}
