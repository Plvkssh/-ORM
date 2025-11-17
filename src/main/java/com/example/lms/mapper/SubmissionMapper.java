package com.example.lms.mapper;

import com.example.lms.dto.SubmissionResponse;
import com.example.lms.dto.CreateSubmissionRequest;
import com.example.lms.model.Assignment;
import com.example.lms.model.Submission;
import com.example.lms.model.User;

public class SubmissionMapper {
    public static SubmissionResponse toResponse(Submission submission) {
        SubmissionResponse response = new SubmissionResponse();
        response.setId(submission.getId());
        response.setAssignmentId(submission.getAssignment() != null ? submission.getAssignment().getId() : null);
        response.setStudentId(submission.getStudent() != null ? submission.getStudent().getId() : null);
        response.setSubmittedAt(submission.getSubmittedAt());
        response.setContent(submission.getContent());
        response.setScore(submission.getScore());
        response.setFeedback(submission.getFeedback());
        return response;
    }

    public static Submission fromRequest(CreateSubmissionRequest request, Assignment assignment, User student) {
        Submission submission = new Submission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setContent(request.getContent());
        return submission;
    }
}
