package com.example.lms.mapper;

import com.example.lms.dto.EnrollmentResponse;
import com.example.lms.dto.CreateEnrollmentRequest;
import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.User;

public class EnrollmentMapper {
    public static EnrollmentResponse toResponse(Enrollment enrollment) {
        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(enrollment.getId());
        response.setStudentId(enrollment.getStudent() != null ? enrollment.getStudent().getId() : null);
        response.setCourseId(enrollment.getCourse() != null ? enrollment.getCourse().getId() : null);
        response.setEnrollDate(enrollment.getEnrollDate());
        response.setStatus(enrollment.getStatus());
        return response;
    }

    public static Enrollment fromRequest(CreateEnrollmentRequest request, User student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        if (request.getEnrollDate() != null) {
            enrollment.setEnrollDate(request.getEnrollDate());
        }
        if (request.getStatus() != null) {
            enrollment.setStatus(request.getStatus());
        }
        return enrollment;
    }
}
