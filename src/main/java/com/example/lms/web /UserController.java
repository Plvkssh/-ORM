package com.example.lms.web;

import com.example.lms.dto.UserResponse;
import com.example.lms.dto.CreateUserRequest;
import com.example.lms.mapper.UserMapper;
import com.example.lms.model.User;
import com.example.lms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для управления пользователями через REST API.
 * Предоставляет endpoints для операций CRUD над пользователями различных ролей.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Получает всех пользователей системы.
     *
     * @return список всех пользователей в формате DTO
     */
    @GetMapping
    public List<UserResponse> findAll() { 
        return userService.findAll().stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    /**
     * Находит пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return пользователь в формате DTO
     */
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) { 
        return UserMapper.toResponse(userService.getById(id)); 
    }

    /**
     * Создаёт нового пользователя.
     *
     * @param request данные для создания пользователя
     * @return созданный пользователь в формате DTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) { 
        User created = userService.create(UserMapper.fromRequest(request)); 
        return UserMapper.toResponse(created); 
    }

    /**
     * Обновляет существующего пользователя.
     *
     * @param id идентификатор обновляемого пользователя
     * @param request обновлённые данные пользователя
     * @return обновлённый пользователь в формате DTO
     */
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody CreateUserRequest request) { 
        User updated = userService.update(id, UserMapper.fromRequest(request)); 
        return UserMapper.toResponse(updated); 
    }

    /**
     * Удаляет пользователя.
     *
     * @param id идентификатор удаляемого пользователя
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        userService.delete(id); 
    }
}
