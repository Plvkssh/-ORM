package com.example.lms.service;

import com.example.lms.model.CourseReview;
import com.example.lms.repository.CourseReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис для управления отзывами на курсы.
 * Обрабатывает создание, получение, обновление и удаление отзывов студентов о курсах.
 */
@Service
@Transactional
public class CourseReviewService {
    
    private final CourseReviewRepository courseReviewRepository;

    public CourseReviewService(CourseReviewRepository courseReviewRepository) {
        this.courseReviewRepository = courseReviewRepository;
    }

    /**
     * Получает все отзывы из системы.
     *
     * @return список всех отзывов на курсы
     */
    public List<CourseReview> findAll() { 
        return courseReviewRepository.findAll(); 
    }

    /**
     * Находит отзыв по его идентификатору.
     *
     * @param id идентификатор отзыва
     * @return найденный отзыв
     * @throws NoSuchElementException если отзыв с указанным ID не существует
     */
    public CourseReview getById(Long id) { 
        return courseReviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Review not found")); 
    }

    /**
     * Создаёт новый отзыв на курс.
     *
     * @param review отзыв для создания
     * @return сохранённый отзыв с присвоенным ID
     */
    public CourseReview create(CourseReview review) { 
        return courseReviewRepository.save(review); 
    }

    /**
     * Обновляет существующий отзыв.
     *
     * @param id идентификатор обновляемого отзыва
     * @param updated обновлённые данные отзыва
     * @return сохранённый обновлённый отзыв
     * @throws NoSuchElementException если отзыв с указанным ID не существует
     */
    public CourseReview update(Long id, CourseReview updated) {
        CourseReview existingReview = getById(id);
        existingReview.setCourse(updated.getCourse());
        existingReview.setStudent(updated.getStudent());
        existingReview.setRating(updated.getRating());
        existingReview.setComment(updated.getComment());
        return courseReviewRepository.save(existingReview);
    }

    /**
     * Удаляет отзыв из системы.
     *
     * @param id идентификатор удаляемого отзыва
     */
    public void delete(Long id) { 
        courseReviewRepository.deleteById(id); 
    }
}
