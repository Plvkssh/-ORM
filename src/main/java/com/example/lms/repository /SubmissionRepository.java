package com.example.lms.repository;

import com.example.lms.model.Assignment;
import com.example.lms.model.Submission;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления решениями заданий студентами.
 * Хранит выполненные работы студентов и оценки преподавателей.
 */
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    
    /**
     * Находит все решения для указанного задания.
     * Используется преподавателями для проверки работ всех студентов.
     *
     * @param assignment задание, для которого нужно найти решения
     * @return список решений данного задания
     */
    List<Submission> findByAssignment(Assignment assignment);
    
    /**
     * Находит все решения, отправленные указанным студентом.
     * Используется для просмотра истории выполненных работ студента.
     *
     * @param student студент, чьи решения нужно найти
     * @return список решений данного студента
     */
    List<Submission> findByStudent(User student);
    
    /**
     * Находит решение конкретного студента для конкретного задания.
     * Используется для проверки, отправил ли студент работу, и для её обновления.
     *
     * @param student студент, чьё решение ищется
     * @param assignment задание, для которого ищется решение
     * @return Optional с решением, если студент отправил работу
     */
    Optional<Submission> findByStudentAndAssignment(User student, Assignment assignment);
}
