package com.example.lms.service;

import com.example.lms.model.Assignment;
import com.example.lms.model.Submission;
import com.example.lms.model.User;
import com.example.lms.repository.AssignmentRepository;
import com.example.lms.repository.SubmissionRepository;
import com.example.lms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис для управления решениями заданий студентами.
 * Обрабатывает отправку решений, проверку дубликатов и оценку работ преподавателями.
 */
@Service
@Transactional
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public SubmissionService(SubmissionRepository submissionRepository, 
                            AssignmentRepository assignmentRepository, 
                            UserRepository userRepository) {
        this.submissionRepository = submissionRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Получает все решения заданий из системы.
     *
     * @return список всех решений
     */
    public List<Submission> findAll() { 
        return submissionRepository.findAll(); 
    }

    /**
     * Находит решение задания по его идентификатору.
     *
     * @param id идентификатор решения
     * @return найденное решение
     * @throws NoSuchElementException если решение с указанным ID не существует
     */
    public Submission getById(Long id) { 
        return submissionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Submission not found")); 
    }

    /**
     * Получает все решения для указанного задания.
     *
     * @param assignmentId идентификатор задания
     * @return список решений данного задания
     * @throws NoSuchElementException если задание не найдено
     */
    public List<Submission> getByAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NoSuchElementException("Assignment not found"));
        return submissionRepository.findByAssignment(assignment);
    }

    /**
     * Получает все решения, отправленные указанным студентом.
     *
     * @param studentId идентификатор студента
     * @return список решений данного студента
     * @throws NoSuchElementException если студент не найден
     */
    public List<Submission> getByStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
        return submissionRepository.findByStudent(student);
    }

    /**
     * Создаёт новое решение задания с проверкой на дублирование.
     * Каждый студент может отправить только одно решение на каждое задание.
     *
     * @param submission решение для создания
     * @return сохранённое решение с присвоенным ID
     * @throws IllegalStateException если студент уже отправил решение этого задания
     */
    public Submission create(Submission submission) {
        if (submissionRepository.findByStudentAndAssignment(submission.getStudent(), 
                submission.getAssignment()).isPresent()) {
            throw new IllegalStateException("Student has already submitted this assignment");
        }
        return submissionRepository.save(submission);
    }

    /**
     * Оценивает решение задания преподавателем.
     * Устанавливает баллы и обратную связь для решения студента.
     *
     * @param id идентификатор оцениваемого решения
     * @param score полученные баллы
     * @param feedback обратная связь от преподавателя
     * @return обновлённое решение с оценкой
     * @throws NoSuchElementException если решение с указанным ID не существует
     */
    public Submission grade(Long id, Integer score, String feedback) {
        Submission submission = getById(id);
        submission.setScore(score);
        submission.setFeedback(feedback);
        return submissionRepository.save(submission);
    }

    /**
     * Удаляет решение задания из системы.
     *
     * @param id идентификатор удаляемого решения
     */
    public void delete(Long id) { 
        submissionRepository.deleteById(id); 
    }
}
