package com.example.lms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments", uniqueConstraints = {
        @UniqueConstraint(name = "uk_enrollment_user_course", columnNames = {"student_id", "course_id"})
})
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, foreignKey = @ForeignKey(name = "fk_enrollment_user"))
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false, foreignKey = @ForeignKey(name = "fk_enrollment_course"))
    private Course course;

    @Column(nullable = false)
    private LocalDate enrollDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    // Конструкторы
    public Enrollment() {}
    
    public Enrollment(User student, Course course, EnrollmentStatus status) {
        this.student = student;
        this.course = course;
        this.status = status;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public User getStudent() { 
        return student; 
    }
    
    public void setStudent(User student) { 
        this.student = student; 
    }
    
    public Course getCourse() { 
        return course; 
    }
    
    public void setCourse(Course course) { 
        this.course = course; 
    }
    
    public LocalDate getEnrollDate() { 
        return enrollDate; 
    }
    
    public void setEnrollDate(LocalDate enrollDate) { 
        this.enrollDate = enrollDate; 
    }
    
    public EnrollmentStatus getStatus() { 
        return status; 
    }
    
    public void setStatus(EnrollmentStatus status) { 
        this.status = status; 
    }
}
