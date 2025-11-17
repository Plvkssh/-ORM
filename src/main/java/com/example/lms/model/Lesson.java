package com.example.lms.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false, foreignKey = @ForeignKey(name = "fk_lesson_module"))
    private Module module;

    @Column(nullable = false)
    private String title;

    @Column(length = 10000)
    private String content;

    private String videoUrl;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments = new ArrayList<>();

    // Конструкторы
    public Lesson() {}
    
    public Lesson(Module module, String title, String content, String videoUrl) {
        this.module = module;
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
    }

    // Геттеры и сеттеры
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Module getModule() { 
        return module; 
    }
    
    public void setModule(Module module) { 
        this.module = module; 
    }
    
    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    public String getContent() { 
        return content; 
    }
    
    public void setContent(String content) { 
        this.content = content; 
    }
    
    public String getVideoUrl() { 
        return videoUrl; 
    }
    
    public void setVideoUrl(String videoUrl) { 
        this.videoUrl = videoUrl; 
    }
    
    public List<Assignment> getAssignments() { 
        return assignments; 
    }
    
    public void setAssignments(List<Assignment> assignments) { 
        this.assignments = assignments; 
    }
}
