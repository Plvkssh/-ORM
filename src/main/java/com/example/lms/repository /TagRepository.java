package com.example.lms.repository;

import com.example.lms.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для управления тегами курсов.
 * Теги используются для категоризации и фильтрации курсов по темам.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    /**
     * Находит тег по его названию.
     * Используется для предотвращения создания дубликатов тегов.
     *
     * @param name название тега для поиска
     * @return Optional с найденным тегом или пустой, если тег не существует
     */
    Optional<Tag> findByName(String name);
}
