package com.example.lms.service;

import com.example.lms.model.Course;
import com.example.lms.model.Lesson;
import com.example.lms.model.Module;
import com.example.lms.model.User;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.LessonRepository;
import com.example.lms.repository.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    public CourseService(CourseRepository courseRepository, ModuleRepository moduleRepository, 
                        LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    public List<Course> findAll() { 
        return courseRepository.findAll(); 
    }

    /**
     * Возвращает курс по ID. Если курс не найден, выбрасывает исключение.
     */
    public Course getById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));
    }

    /**
     * Находит все курсы указанного преподавателя.
     */
    public List<Course> findByTeacher(User teacher) { 
        return courseRepository.findByTeacher(teacher); 
    }

    public Course create(Course course) { 
        return courseRepository.save(course); 
    }

    /**
     * Обновляет существующий курс. Сначала проверяет его существование.
     */
    public Course update(Long id, Course updatedCourse) {
        Course existingCourse = getById(id);
        existingCourse.setTitle(updatedCourse.getTitle());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setDuration(updatedCourse.getDuration());
        existingCourse.setStartDate(updatedCourse.getStartDate());
        existingCourse.setCategory(updatedCourse.getCategory());
        existingCourse.setTeacher(updatedCourse.getTeacher());
        existingCourse.setTags(updatedCourse.getTags());
        return courseRepository.save(existingCourse);
    }

    /**
     * Добавляет новый модуль к курсу. Сначала проверяет существование курса.
     */
    public Module addModule(Long courseId, Module module) {
        Course course = getById(courseId);
        module.setCourse(course);
        return moduleRepository.save(module);
    }

    /**
     * Добавляет новый урок к модулю. Если модуль не найден, выбрасывает исключение.
     */
    public Lesson addLesson(Long moduleId, Lesson lesson) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new NoSuchElementException("Module not found"));
        lesson.setModule(module);
        return lessonRepository.save(lesson);
    }

    public void delete(Long id) { 
        courseRepository.deleteById(id); 
    }
}
