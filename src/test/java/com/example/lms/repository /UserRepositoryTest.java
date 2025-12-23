package com.example.lms.repository;

import com.example.lms.model.User;
import com.example.lms.model.UserRole;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест для репозитория пользователей.
 * Проверяет базовые операции сохранения и поиска пользователей по email.
 */
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest extends PostgresContainerTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Проверяет корректность сохранения пользователя и поиска его по email адресу.
     * Тест создаёт пользователя, сохраняет его в базу данных, затем ищет по email
     * и проверяет, что найденный пользователь содержит корректные данные.
     */
    @Test
    void saveAndFindByEmail() {
        // Создание и сохранение пользователя
        User user = new User();
        user.setName("Алиса Иванова");
        user.setEmail("alice.ivanova@example.com");
        user.setRole(UserRole.STUDENT);
        userRepository.save(user);

        // Поиск пользователя по email
        Optional<User> foundUser = userRepository.findByEmail("alice.ivanova@example.com");
        
        // Проверка результатов
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Алиса Иванова");
        assertThat(foundUser.get().getRole()).isEqualTo(UserRole.STUDENT);
    }
}
