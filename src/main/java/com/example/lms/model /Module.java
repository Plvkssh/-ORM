package com.example.lms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false, foreignKey = @ForeignKey(name = "fk_module_course"))
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer orderIndex;

    @Column(length = 2000)
    private String description;

    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToOne(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Quiz quiz;

    // Конструкторы
    public Module() {}
    
    public Module(Course course, String title, Integer orderIndex, String description) {
        this.course = course;
        this.title = title;
        this.orderIndex = orderIndex;
        this.description = description;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Course getCourse() { 
        return course; 
    }
    
    public void setCourse(Course course) { 
        this.course = course; 
    }
    
    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    public Integer getOrderIndex() { 
        return orderIndex; 
    }
    
    public void setOrderIndex(Integer orderIndex) { 
        this.orderIndex = orderIndex; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public List<Lesson> getLessons() { 
        return lessons; 
    }
    
    public void setLessons(List<Lesson> lessons) { 
        this.lessons = lessons; 
    }
    
    public Quiz getQuiz() { 
        return quiz; 
    }
    
    public void setQuiz(Quiz quiz) { 
        this.quiz = quiz; 
    }
}
