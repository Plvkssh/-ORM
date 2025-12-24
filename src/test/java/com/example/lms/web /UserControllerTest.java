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

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends PostgresContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndGetUser() throws Exception {
        String userRequestJson = String.format("""
            {
              "name": "Мария Сидорова",
              "email": "maria.sidorova@example.com",
              "role": "%s"
            }
            """, UserRole.STUDENT);

        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Мария Сидорова")))
                .andExpect(jsonPath("$.email", is("maria.sidorova@example.com")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }
}
