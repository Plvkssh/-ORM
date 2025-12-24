package com.example.lms.service;

import com.example.lms.model.User;
import com.example.lms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Возвращает пользователя по ID. Если пользователь не найден, выбрасывает исключение.
     */
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    /**
     * Обновляет существующего пользователя. Сначала проверяет его существование.
     */
    public User update(Long id, User updatedUser) {
        User existingUser = getById(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());
        return userRepository.save(existingUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
