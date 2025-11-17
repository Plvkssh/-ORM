package com.example.lms.mapper;

import com.example.lms.dto.CourseReviewResponse;
import com.example.lms.dto.CreateCourseReviewRequest;
import com.example.lms.model.Course;
import com.example.lms.model.CourseReview;
import com.example.lms.model.User;

public class CourseReviewMapper {
    public static CourseReviewResponse toResponse(CourseReview review) {
        CourseReviewResponse response = new CourseReviewResponse();
        response.setId(review.getId());
        response.setCourseId(review.getCourse() != null ? review.getCourse().getId() : null);
        response.setStudentId(review.getStudent() != null ? review.getStudent().getId() : null);
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setCreatedAt(review.getCreatedAt());
        return response;
    }

    public static CourseReview fromRequest(CreateCourseReviewRequest request, Course course, User student) {
        CourseReview review = new CourseReview();
        review.setCourse(course);
        review.setStudent(student);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return review;
    }
}
