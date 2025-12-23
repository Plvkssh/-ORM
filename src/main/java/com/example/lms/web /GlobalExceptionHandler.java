package com.example.lms.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Глобальный обработчик исключений для REST API.
 * Предоставляет единообразные ответы на ошибки для всех контроллеров.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключения при отсутствии сущностей (404 Not Found).
     * Используется когда не найден курс, студент, задание и т.д.
     *
     * @param exception исключение "элемент не найден"
     * @return ResponseEntity с описанием ошибки и статусом 404
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoSuchElementException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Not Found");
        errorResponse.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Обрабатывает ошибки валидации входных данных (400 Bad Request).
     * Собирает все ошибки валидации по полям для удобного отображения клиенту.
     *
     * @param exception исключение валидации данных
     * @return ResponseEntity с детализацией ошибок валидации и статусом 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Validation failed");
        errorResponse.put("details", validationErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Обрабатывает бизнес-ошибки (400 Bad Request).
     * Используется для нарушений бизнес-правил, например, попытка повторной записи на курс.
     *
     * @param exception исключение нарушения бизнес-правил
     * @return ResponseEntity с описанием ошибки и статусом 400
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Invalid operation");
        errorResponse.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
