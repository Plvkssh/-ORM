package com.example.lms.service;

import com.example.lms.model.CourseReview;
import com.example.lms.repository.CourseReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CourseReviewService {
    
    private final CourseReviewRepository courseReviewRepository;

    public CourseReviewService(CourseReviewRepository courseReviewRepository) {
        this.courseReviewRepository = courseReviewRepository;
    }

    public List<CourseReview> findAll() { 
        return courseReviewRepository.findAll(); 
    }

    /**
     * Возвращает отзыв по ID. Если отзыв не найден, выбрасывает исключение.
     */
    public CourseReview getById(Long id) { 
        return courseReviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Review not found")); 
    }

    public CourseReview create(CourseReview review) { 
        return courseReviewRepository.save(review); 
    }

    /**
     * Обновляет существующий отзыв. Сначала проверяет его существование.
     */
    public CourseReview update(Long id, CourseReview updatedReview) {
        CourseReview existingReview = getById(id);
        existingReview.setCourse(updatedReview.getCourse());
        existingReview.setStudent(updatedReview.getStudent());
        existingReview.setRating(updatedReview.getRating());
        existingReview.setComment(updatedReview.getComment());
        return courseReviewRepository.save(existingReview);
    }

    public void delete(Long id) { 
        courseReviewRepository.deleteById(id); 
    }
}
