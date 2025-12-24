package com.example.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // OpenAPI документация
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        
                        // Административные endpoint-ы
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        
                        // Ресурсы для преподавателей
                        .requestMatchers("/api/submissions/**", "/api/quiz-submissions/**")
                        .hasAnyRole("TEACHER", "ADMIN")
                        
                        // Остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = createUser("admin", "admin", "ADMIN");
        UserDetails teacher = createUser("teacher", "teacher", "TEACHER");
        UserDetails student = createUser("student", "student", "STUDENT");
        
        return new InMemoryUserDetailsManager(admin, teacher, student);
    }
    
    private UserDetails createUser(String username, String password, String role) {
        return User.withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .roles(role)
                .build();
    }
}
