package com.example.lms.repository;

import com.example.lms.model.Assignment;
import com.example.lms.model.Submission;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    
    List<Submission> findByAssignment(Assignment assignment);
    
    List<Submission> findByStudent(User student);
    
    Optional<Submission> findByStudentAndAssignment(User student, Assignment assignment);
}
