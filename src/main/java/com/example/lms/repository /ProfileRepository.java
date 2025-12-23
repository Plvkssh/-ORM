package com.example.lms.repository;

import com.example.lms.model.Profile;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для управления профилями пользователей.
 * Профили содержат дополнительную информацию о пользователях системы.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    /**
     * Находит профиль по связанному пользователю.
     * Используется для получения расширенной информации о пользователе.
     *
     * @param user пользователь, чей профиль нужно найти
     * @return Optional с профилем пользователя, если существует
     */
    Optional<Profile> findByUser(User user);
}
