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

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest extends PostgresContainerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindByEmail() {
        User user = new User();
        user.setName("Иван Петров");
        user.setEmail("ivan.petrov@example.com");
        user.setRole(UserRole.STUDENT);
        userRepository.save(user);

        Optional<User> userOptional = userRepository.findByEmail("ivan.petrov@example.com");
        
        assertThat(userOptional).isPresent();
        assertThat(userOptional.get().getName()).isEqualTo("Иван Петров");
        assertThat(userOptional.get().getRole()).isEqualTo(UserRole.STUDENT);
    }
}
