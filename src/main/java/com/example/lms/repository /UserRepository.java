package com.example.lms.repository;

import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для управления пользователями системы.
 * Обеспечивает доступ к данным пользователей и их аутентификацию.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Находит пользователя по email адресу.
     * Используется для аутентификации и проверки уникальности email при регистрации.
     *
     * @param email email адрес для поиска пользователя
     * @return Optional с найденным пользователем или пустой, если пользователь не найден
     */
    Optional<User> findByEmail(String email);
}
