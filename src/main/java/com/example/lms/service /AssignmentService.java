package com.example.lms.service;

import com.example.lms.model.Assignment;
import com.example.lms.repository.AssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> findAll() { 
        return assignmentRepository.findAll(); 
    }

    /**
     * Возвращает задание по ID. Если задание не найдено, выбрасывает исключение.
     */
    public Assignment getById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Assignment not found"));
    }

    public Assignment create(Assignment assignment) { 
        return assignmentRepository.save(assignment); 
    }

    /**
     * Обновляет существующее задание. Сначала проверяет его существование.
     */
    public Assignment update(Long id, Assignment updatedAssignment) {
        Assignment existingAssignment = getById(id);
        
        existingAssignment.setLesson(updatedAssignment.getLesson());
        existingAssignment.setTitle(updatedAssignment.getTitle());
        existingAssignment.setDescription(updatedAssignment.getDescription());
        existingAssignment.setDueDate(updatedAssignment.getDueDate());
        existingAssignment.setMaxScore(updatedAssignment.getMaxScore());
        
        return assignmentRepository.save(existingAssignment);
    }

    public void delete(Long id) { 
        assignmentRepository.deleteById(id); 
    }
}
