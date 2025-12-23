package com.example.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения Learning Management System (LMS).
 * Точка входа для Spring Boot приложения системы управления обучением.
 * 
 * Приложение предоставляет функционал для:
 * - Управления курсами, модулями и уроками
 * - Регистрации студентов на курсы
 * - Проведения тестов и заданий
 * - Оценки работ преподавателями
 * - Оставления отзывов о курсах
 */
@SpringBootApplication
public class LmsApplication {

    /**
     * Точка входа в приложение.
     * Запускает Spring Boot приложение с конфигурацией по умолчанию.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        SpringApplication.run(LmsApplication.class, args);
    }
}
