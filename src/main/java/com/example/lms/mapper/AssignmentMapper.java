package com.example.lms.mapper;

import com.example.lms.dto.AssignmentResponse;
import com.example.lms.dto.CreateAssignmentRequest;
import com.example.lms.model.Assignment;
import com.example.lms.model.Lesson;

public class AssignmentMapper {
    public static AssignmentResponse toResponse(Assignment assignment) {
        AssignmentResponse response = new AssignmentResponse();
        response.setId(assignment.getId());
        response.setLessonId(assignment.getLesson() != null ? assignment.getLesson().getId() : null);
        response.setTitle(assignment.getTitle());
        response.setDescription(assignment.getDescription());
        response.setDueDate(assignment.getDueDate());
        response.setMaxScore(assignment.getMaxScore());
        return response;
    }

    public static Assignment fromRequest(CreateAssignmentRequest request, Lesson lesson) {
        Assignment assignment = new Assignment();
        assignment.setLesson(lesson);
        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment.setDueDate(request.getDueDate());
        assignment.setMaxScore(request.getMaxScore());
        return assignment;
    }

    public static void updateEntity(Assignment existingAssignment, CreateAssignmentRequest request, Lesson lesson) {
        existingAssignment.setLesson(lesson);
        existingAssignment.setTitle(request.getTitle());
        existingAssignment.setDescription(request.getDescription());
        existingAssignment.setDueDate(request.getDueDate());
        existingAssignment.setMaxScore(request.getMaxScore());
    }
}
