package com.example.lms.web;

import com.example.lms.model.UserRole;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Интеграционный тест для контроллера пользователей (UserController).
 * Проверяет работу REST endpoints для создания и получения пользователей системы.
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends PostgresContainerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Тест создания нового пользователя и получения списка пользователей.
     * Проверяет полный цикл работы контроллера пользователей:
     * 1. Создание пользователя через POST запрос
     * 2. Проверка корректности созданного пользователя
     * 3. Получение списка всех пользователей через GET запрос
     * 
     * @throws Exception если происходит ошибка в MockMvc
     */
    @Test
    void createAndGetUser() throws Exception {
        // Подготовка JSON тела запроса для создания пользователя
        String userRequestJson = String.format("""
            {
              "name": "Александр Петров",
              "email": "alexander.petrov@example.com",
              "role": "%s"
            }
            """, UserRole.STUDENT);

        // Создание пользователя и проверка ответа
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Александр Петров")))
                .andExpect(jsonPath("$.email", is("alexander.petrov@example.com")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Получение списка всех пользователей
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }
}
