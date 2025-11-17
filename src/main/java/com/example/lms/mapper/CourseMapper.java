package com.example.lms.mapper;

import com.example.lms.dto.CourseResponse;
import com.example.lms.dto.CreateCourseRequest;
import com.example.lms.model.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseMapper {

    public static CourseResponse toResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
        response.setDuration(course.getDuration());
        response.setStartDate(course.getStartDate());
        response.setCategoryId(course.getCategory() != null ? course.getCategory().getId() : null);
        response.setTeacherId(course.getTeacher() != null ? course.getTeacher().getId() : null);
        Set<Long> tagIds = course.getTags() == null ? new HashSet<>() : course.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        response.setTagIds(tagIds);
        return response;
    }

    public static Course fromRequest(CreateCourseRequest request, Category category, User teacher, Set<Tag> tags) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setDuration(request.getDuration());
        course.setStartDate(request.getStartDate());
        course.setCategory(category);
        course.setTeacher(teacher);
        course.setTags(tags);
        return course;
    }

    public static void updateEntity(Course existingCourse, CreateCourseRequest request, Category category, User teacher, Set<Tag> tags) {
        existingCourse.setTitle(request.getTitle());
        existingCourse.setDescription(request.getDescription());
        existingCourse.setDuration(request.getDuration());
        existingCourse.setStartDate(request.getStartDate());
        existingCourse.setCategory(category);
        existingCourse.setTeacher(teacher);
        existingCourse.setTags(tags);
    }
}
