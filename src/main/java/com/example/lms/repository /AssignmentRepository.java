package com.example.lms.repository;

import com.example.lms.model.Assignment;
import com.example.lms.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с заданиями в системе обучения.
 * Предоставляет стандартные CRUD-операции через JpaRepository.
 */
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    /**
     * Находит все задания, принадлежащие конкретному уроку.
     *
     * @param lesson урок, для которого нужно найти задания
     * @return список заданий урока
     */
    List<Assignment> findByLesson(Lesson lesson);
}
