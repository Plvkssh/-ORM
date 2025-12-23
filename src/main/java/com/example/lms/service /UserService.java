package com.example.lms.service;

import com.example.lms.model.User;
import com.example.lms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис для управления пользователями системы обучения.
 * Обеспечивает операции CRUD для пользователей различных ролей (студенты, преподаватели, администраторы).
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Получает всех пользователей системы.
     *
     * @return список всех пользователей
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Находит пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь
     * @throws NoSuchElementException если пользователь с указанным ID не существует
     */
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    /**
     * Создаёт нового пользователя в системе.
     *
     * @param user пользователь для создания
     * @return сохранённый пользователь с присвоенным ID
     */
    public User create(User user) {
        return userRepository.save(user);
    }

    /**
     * Обновляет существующего пользователя.
     * Изменяет основные атрибуты пользователя: имя, email и роль.
     *
     * @param id идентификатор обновляемого пользователя
     * @param updated обновлённые данные пользователя
     * @return сохранённый обновлённый пользователь
     * @throws NoSuchElementException если пользователь с указанным ID не существует
     */
    public User update(Long id, User updated) {
        User existingUser = getById(id);
        existingUser.setName(updated.getName());
        existingUser.setEmail(updated.getEmail());
        existingUser.setRole(updated.getRole());
        return userRepository.save(existingUser);
    }

    /**
     * Удаляет пользователя из системы.
     * Внимание: может нарушить ссылочную целостность при наличии связанных сущностей.
     *
     * @param id идентификатор удаляемого пользователя
     */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
