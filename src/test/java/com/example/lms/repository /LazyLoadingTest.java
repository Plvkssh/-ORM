package com.example.lms.repository;

import com.example.lms.model.*;
import com.example.lms.support.PostgresContainerTest;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class LazyLoadingTest extends PostgresContainerTest {

    @Autowired 
    private CourseRepository courseRepository;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired 
    private ModuleRepository moduleRepository;
    
    @Autowired 
    private TransactionTemplate transactionTemplate;

    @Test
    void accessingLazyCollectionOutsideTx_throwsLazyInitializationException() {
        Long courseId = transactionTemplate.execute(status -> {
            User teacher = new User();
            teacher.setName("Тестовый преподаватель");
            teacher.setEmail("teacher.lazy@example.com");
            teacher.setRole(UserRole.TEACHER);
            teacher = userRepository.save(teacher);

            Course course = new Course();
            course.setTitle("Курс для теста ленивой загрузки");
            course.setTeacher(teacher);
            course = courseRepository.save(course);

            Module module = new Module();
            module.setCourse(course);
            module.setTitle("Тестовый модуль");
            module.setOrderIndex(1);
            moduleRepository.save(module);
            
            return course.getId();
        });

        Course course = courseRepository.findById(courseId).orElseThrow();
        
        assertThatThrownBy(() -> course.getModules().size())
                .isInstanceOf(LazyInitializationException.class);
    }
}
