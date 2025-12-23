package com.example.lms.service;

import com.example.lms.model.Lesson;
import com.example.lms.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис для управления уроками в системе обучения.
 * Обеспечивает базовые CRUD-операции для уроков, входящих в состав модулей.
 */
@Service
@Transactional
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    /**
     * Получает все уроки из системы.
     *
     * @return список всех уроков
     */
    public List<Lesson> findAll() { 
        return lessonRepository.findAll(); 
    }

    /**
     * Находит урок по его идентификатору.
     *
     * @param id идентификатор урока
     * @return найденный урок
     * @throws NoSuchElementException если урок с указанным ID не существует
     */
    public Lesson getById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lesson not found"));
    }

    /**
     * Создаёт новый урок в системе.
     *
     * @param lesson урок для создания
     * @return сохранённый урок с присвоенным ID
     */
    public Lesson create(Lesson lesson) { 
        return lessonRepository.save(lesson); 
    }

    /**
     * Обновляет существующий урок.
     *
     * @param id идентификатор обновляемого урока
     * @param updated обновлённые данные урока
     * @return сохранённый обновлённый урок
     * @throws NoSuchElementException если урок с указанным ID не существует
     */
    public Lesson update(Long id, Lesson updated) {
        Lesson existingLesson = getById(id);
        existingLesson.setModule(updated.getModule());
        existingLesson.setTitle(updated.getTitle());
        existingLesson.setContent(updated.getContent());
        existingLesson.setVideoUrl(updated.getVideoUrl());
        return lessonRepository.save(existingLesson);
    }

    /**
     * Удаляет урок из системы.
     *
     * @param id идентификатор удаляемого урока
     */
    public void delete(Long id) { 
        lessonRepository.deleteById(id); 
    }
}
