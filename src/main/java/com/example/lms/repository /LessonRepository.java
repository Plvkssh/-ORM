package com.example.lms.repository;

import com.example.lms.model.Lesson;
import com.example.lms.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с уроками в системе обучения.
 * Управляет уроками, которые входят в состав модулей курсов.
 */
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    
    /**
     * Находит все уроки, принадлежащие указанному модулю.
     * Используется для построения структуры курса и навигации по урокам.
     *
     * @param module модуль, для которого нужно найти уроки
     * @return список уроков в данном модуле
     */
    List<Lesson> findByModule(Module module);
}
