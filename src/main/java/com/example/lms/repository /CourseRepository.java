package com.example.lms.repository;

import com.example.lms.model.Course;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для управления курсами в системе обучения.
 * Предоставляет расширенные методы поиска помимо стандартных CRUD-операций.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    /**
     * Находит все курсы, которые ведёт указанный преподаватель.
     *
     * @param teacher преподаватель, чьи курсы нужно найти
     * @return список курсов преподавателя
     */
    List<Course> findByTeacher(User teacher);
    
    /**
     * Находит курсы по названию категории.
     * Использует navigation property для доступа к полю name сущности Category.
     *
     * @param name название категории для фильтрации курсов
     * @return список курсов указанной категории
     */
    List<Course> findByCategory_Name(String name);
}
