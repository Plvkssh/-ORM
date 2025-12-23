package com.example.lms.repository;

import com.example.lms.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для управления категориями курсов.
 * Наследует стандартные операции JPA для работы с сущностью Category.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Ищет категорию по её названию.
     * Используется для проверки уникальности категорий.
     *
     * @param name название категории для поиска
     * @return Optional с найденной категорией или пустой, если не найдена
     */
    Optional<Category> findByName(String name);
}
