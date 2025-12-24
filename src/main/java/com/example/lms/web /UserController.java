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

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> findAll() { 
        return userService.findAll().stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) { 
        return UserMapper.toResponse(userService.getById(id)); 
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) { 
        User user = userService.create(UserMapper.fromRequest(request)); 
        return UserMapper.toResponse(user); 
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody CreateUserRequest request) { 
        User user = userService.update(id, UserMapper.fromRequest(request)); 
        return UserMapper.toResponse(user); 
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        userService.delete(id); 
    }
}
