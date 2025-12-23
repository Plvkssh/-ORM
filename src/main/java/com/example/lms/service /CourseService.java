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

/**
 * Сервис для управления курсами и их структурой.
 * Обеспечивает операции с курсами, модулями и уроками, включая построение иерархии.
 */
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

    /**
     * Получает все курсы из системы.
     *
     * @return список всех курсов
     */
    public List<Course> findAll() { 
        return courseRepository.findAll(); 
    }

    /**
     * Находит курс по его идентификатору.
     *
     * @param id идентификатор курса
     * @return найденный курс
     * @throws NoSuchElementException если курс с указанным ID не существует
     */
    public Course getById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));
    }

    /**
     * Находит все курсы, которые ведёт указанный преподаватель.
     *
     * @param teacher преподаватель, чьи курсы нужно найти
     * @return список курсов преподавателя
     */
    public List<Course> findByTeacher(User teacher) { 
        return courseRepository.findByTeacher(teacher); 
    }

    /**
     * Создаёт новый курс в системе.
     *
     * @param course курс для создания
     * @return сохранённый курс с присвоенным ID
     */
    public Course create(Course course) { 
        return courseRepository.save(course); 
    }

    /**
     * Обновляет существующий курс.
     *
     * @param id идентификатор обновляемого курса
     * @param updated обновлённые данные курса
     * @return сохранённый обновлённый курс
     * @throws NoSuchElementException если курс с указанным ID не существует
     */
    public Course update(Long id, Course updated) {
        Course existingCourse = getById(id);
        existingCourse.setTitle(updated.getTitle());
        existingCourse.setDescription(updated.getDescription());
        existingCourse.setDuration(updated.getDuration());
        existingCourse.setStartDate(updated.getStartDate());
        existingCourse.setCategory(updated.getCategory());
        existingCourse.setTeacher(updated.getTeacher());
        existingCourse.setTags(updated.getTags());
        return courseRepository.save(existingCourse);
    }

    /**
     * Добавляет новый модуль к существующему курсу.
     *
     * @param courseId идентификатор курса, к которому добавляется модуль
     * @param module модуль для добавления
     * @return сохранённый модуль с установленной связью с курсом
     * @throws NoSuchElementException если курс с указанным ID не существует
     */
    public Module addModule(Long courseId, Module module) {
        Course course = getById(courseId);
        module.setCourse(course);
        return moduleRepository.save(module);
    }

    /**
     * Добавляет новый урок к существующему модулю.
     *
     * @param moduleId идентификатор модуля, к которому добавляется урок
     * @param lesson урок для добавления
     * @return сохранённый урок с установленной связью с модулем
     * @throws NoSuchElementException если модуль с указанным ID не существует
     */
    public Lesson addLesson(Long moduleId, Lesson lesson) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new NoSuchElementException("Module not found"));
        lesson.setModule(module);
        return lessonRepository.save(lesson);
    }

    /**
     * Удаляет курс из системы.
     *
     * @param id идентификатор удаляемого курса
     */
    public void delete(Long id) { 
        courseRepository.deleteById(id); 
    }
}
