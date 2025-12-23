package com.example.lms.service;

import com.example.lms.model.Assignment;
import com.example.lms.repository.AssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис для управления заданиями в системе обучения.
 * Обеспечивает бизнес-логику для операций CRUD над заданиями.
 */
@Service
@Transactional
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * Получает все задания из системы.
     *
     * @return список всех заданий
     */
    public List<Assignment> findAll() { 
        return assignmentRepository.findAll(); 
    }

    /**
     * Находит задание по его идентификатору.
     *
     * @param id идентификатор задания
     * @return найденное задание
     * @throws NoSuchElementException если задание с указанным ID не существует
     */
    public Assignment getById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Assignment not found"));
    }

    /**
     * Создаёт новое задание в системе.
     *
     * @param assignment задание для создания
     * @return сохранённое задание с присвоенным ID
     */
    public Assignment create(Assignment assignment) { 
        return assignmentRepository.save(assignment); 
    }

    /**
     * Обновляет существующее задание.
     *
     * @param id идентификатор обновляемого задания
     * @param updated обновлённые данные задания
     * @return сохранённое обновлённое задание
     * @throws NoSuchElementException если задание с указанным ID не существует
     */
    public Assignment update(Long id, Assignment updated) {
        Assignment existingAssignment = getById(id);
        existingAssignment.setLesson(updated.getLesson());
        existingAssignment.setTitle(updated.getTitle());
        existingAssignment.setDescription(updated.getDescription());
        existingAssignment.setDueDate(updated.getDueDate());
        existingAssignment.setMaxScore(updated.getMaxScore());
        return assignmentRepository.save(existingAssignment);
    }

    /**
     * Удаляет задание из системы.
     *
     * @param id идентификатор удаляемого задания
     */
    public void delete(Long id) { 
        assignmentRepository.deleteById(id); 
    }
}
