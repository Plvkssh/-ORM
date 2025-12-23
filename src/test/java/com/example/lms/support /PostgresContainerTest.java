package com.example.lms.support;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Абстрактный базовый класс для тестов, использующих Testcontainers с PostgreSQL.
 * Предоставляет настройки контейнера PostgreSQL и автоматическую конфигурацию
 * Spring для интеграционных тестов с базой данных.
 * 
 * Все тесты, наследующие от этого класса, будут использовать изолированный
 * контейнер PostgreSQL для выполнения тестов.
 */
@Testcontainers
@ContextConfiguration(initializers = PostgresContainerTest.Initializer.class)
public abstract class PostgresContainerTest {

    /**
     * Контейнер PostgreSQL для выполнения тестов.
     * Использует легковесный образ PostgreSQL 16 на Alpine Linux.
     * Контейнер автоматически запускается перед тестами и останавливается после.
     */
    @Container
    public static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("lms_test")
            .withUsername("postgres")
            .withPassword("postgres");

    /**
     * Инициализатор контекста Spring для настройки свойств подключения к БД.
     * Автоматически подставляет параметры подключения из запущенного контейнера
     * в контекст Spring во время выполнения тестов.
     */
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    // URL для подключения к контейнеру PostgreSQL
                    "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
                    // Имя пользователя БД в контейнере
                    "spring.datasource.username=" + postgresContainer.getUsername(),
                    // Пароль пользователя БД в контейнере
                    "spring.datasource.password=" + postgresContainer.getPassword(),
                    // Стратегия DDL: создание и удаление схемы для каждого теста
                    "spring.jpa.hibernate.ddl-auto=create-drop"
            ).applyTo(context.getEnvironment());
        }
    }
}
