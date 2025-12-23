package com.example.lms.repository;

import com.example.lms.model.Course;
import com.example.lms.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с модулями курсов.
 * Модули являются структурными элементами курсов и содержат уроки.
 */
public interface ModuleRepository extends JpaRepository<Module, Long> {
    
    /**
     * Находит все модули указанного курса, отсортированные по порядку.
     * Сортировка по полю orderIndex обеспечивает правильный порядок изучения.
     *
     * @param course курс, для которого нужно найти модули
     * @return список модулей курса в порядке изучения
     */
    List<Module> findByCourseOrderByOrderIndexAsc(Course course);
}
