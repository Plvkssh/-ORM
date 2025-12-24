package com.example.lms.service;

import com.example.lms.model.Lesson;
import com.example.lms.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> findAll() { 
        return lessonRepository.findAll(); 
    }

    /**
     * Возвращает урок по ID. Если урок не найден, выбрасывает исключение.
     */
    public Lesson getById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lesson not found"));
    }

    public Lesson create(Lesson lesson) { 
        return lessonRepository.save(lesson); 
    }

    /**
     * Обновляет существующий урок. Сначала проверяет его существование.
     */
    public Lesson update(Long id, Lesson updatedLesson) {
        Lesson existingLesson = getById(id);
        existingLesson.setModule(updatedLesson.getModule());
        existingLesson.setTitle(updatedLesson.getTitle());
        existingLesson.setContent(updatedLesson.getContent());
        existingLesson.setVideoUrl(updatedLesson.getVideoUrl());
        return lessonRepository.save(existingLesson);
    }

    public void delete(Long id) { 
        lessonRepository.deleteById(id); 
    }
}
