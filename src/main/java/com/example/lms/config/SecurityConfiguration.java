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
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Публичные эндпоинты для документации
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Административные endpoints
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        // Эндпоинты для преподавателей
                        .requestMatchers("/api/submissions/**", "/api/quiz-submissions/**").hasAnyRole("TEACHER", "ADMIN")
                        // Остальные endpoints публичные
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Демо-пользователи для тестирования (в продакшене заменить на базу данных)
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
                
        UserDetails teacher = User.withDefaultPasswordEncoder()
                .username("teacher")
                .password("teacher")
                .roles("TEACHER")
                .build();
                
        UserDetails student = User.withDefaultPasswordEncoder()
                .username("student")
                .password("student")
                .roles("STUDENT")
                .build();
                
        return new InMemoryUserDetailsManager(admin, teacher, student);
    }
}
