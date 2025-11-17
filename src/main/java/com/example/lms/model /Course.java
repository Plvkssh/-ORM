package com.example.lms.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String description;

    private String duration;

    private LocalDate startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_course_category"))
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false, foreignKey = @ForeignKey(name = "fk_course_teacher"))
    private User teacher;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseReview> reviews = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "course_tag",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    // Конструкторы
    public Course() {}
    
    public Course(String title, String description, String duration, LocalDate startDate, 
                 Category category, User teacher) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.startDate = startDate;
        this.category = category;
        this.teacher = teacher;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public String getDuration() { 
        return duration; 
    }
    
    public void setDuration(String duration) { 
        this.duration = duration; 
    }
    
    public LocalDate getStartDate() { 
        return startDate; 
    }
    
    public void setStartDate(LocalDate startDate) { 
        this.startDate = startDate; 
    }
    
    public Category getCategory() { 
        return category; 
    }
    
    public void setCategory(Category category) { 
        this.category = category; 
    }
    
    public User getTeacher() { 
        return teacher; 
    }
    
    public void setTeacher(User teacher) { 
        this.teacher = teacher; 
    }
    
    public List<Module> getModules() { 
        return modules; 
    }
    
    public void setModules(List<Module> modules) { 
        this.modules = modules; 
    }
    
    public List<Enrollment> getEnrollments() { 
        return enrollments; 
    }
    
    public void setEnrollments(List<Enrollment> enrollments) { 
        this.enrollments = enrollments; 
    }
    
    public List<CourseReview> getReviews() { 
        return reviews; 
    }
    
    public void setReviews(List<CourseReview> reviews) { 
        this.reviews = reviews; 
    }
    
    public Set<Tag> getTags() { 
        return tags; 
    }
    
    public void setTags(Set<Tag> tags) { 
        this.tags = tags; 
    }
}
