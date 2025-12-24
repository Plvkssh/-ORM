package com.example.lms.config;

import com.example.lms.model.*;
import com.example.lms.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Configuration
@Profile("dev")
public class DevelopmentDataLoader {

    @Bean
    CommandLineRunner initializeDemoData(
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            CourseRepository courseRepository,
            ModuleRepository moduleRepository,
            LessonRepository lessonRepository,
            AssignmentRepository assignmentRepository,
            EnrollmentRepository enrollmentRepository,
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            AnswerOptionRepository answerOptionRepository
    ) {
        return args -> {
            User teacher = createTeacher(userRepository);
            User student = createStudent(userRepository);
            
            Category programmingCategory = createCategory(categoryRepository, "Programming");
            Course springCourse = createCourse(
                courseRepository,
                "Основы Spring",
                "Изучение Spring Framework и Spring Boot для создания современных Java-приложений",
                programmingCategory,
                teacher
            );
            
            createModuleWithContent(
                moduleRepository, lessonRepository, assignmentRepository,
                springCourse, 1, "Введение в Spring Framework",
                new String[]{"Введение в Spring", "Dependency Injection и IoC"},
                new String[]{"ДЗ: Настройка первого Spring приложения", "ДЗ: Практика с Dependency Injection"}
            );
            
            createModuleWithContent(
                moduleRepository, lessonRepository, assignmentRepository,
                springCourse, 2, "Spring Boot",
                new String[]{"Основы Spring Boot", "Spring Boot Auto-Configuration"},
                new String[]{"ДЗ: Создание REST API с Spring Boot"}
            );
            
            createModuleWithContent(
                moduleRepository, lessonRepository, assignmentRepository,
                springCourse, 3, "Spring Data JPA",
                new String[]{"Работа с Spring Data JPA"},
                new String[]{"ДЗ: Создание репозиториев и сущностей"}
            );
            
            enrollStudent(enrollmentRepository, springCourse, student);
            createQuizzes(quizRepository, questionRepository, answerOptionRepository);
        };
    }

    private User createTeacher(UserRepository userRepository) {
        return saveUser(userRepository, "Teacher One", "teacher1@example.com", UserRole.TEACHER);
    }

    private User createStudent(UserRepository userRepository) {
        return saveUser(userRepository, "Student One", "student1@example.com", UserRole.STUDENT);
    }

    private User saveUser(UserRepository userRepository, String name, String email, UserRole role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        return userRepository.save(user);
    }

    private Category createCategory(CategoryRepository repository, String name) {
        Category category = new Category();
        category.setName(name);
        return repository.save(category);
    }

    private Course createCourse(CourseRepository repository, String title, String description,
                               Category category, User teacher) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setCategory(category);
        course.setTeacher(teacher);
        course.setStartDate(LocalDate.now());
        return repository.save(course);
    }

    private void createModuleWithContent(
            ModuleRepository moduleRepository,
            LessonRepository lessonRepository,
            AssignmentRepository assignmentRepository,
            Course course,
            int orderIndex,
            String moduleTitle,
            String[] lessonTitles,
            String[] assignmentTitles
    ) {
        Module module = new Module();
        module.setCourse(course);
        module.setTitle(moduleTitle);
        module.setOrderIndex(orderIndex);
        module = moduleRepository.save(module);
        
        for (int i = 0; i < lessonTitles.length; i++) {
            Lesson lesson = createLesson(lessonRepository, module, lessonTitles[i]);
            
            if (i < assignmentTitles.length) {
                createAssignment(assignmentRepository, lesson, assignmentTitles[i]);
            }
        }
    }

    private Lesson createLesson(LessonRepository repository, Module module, String title) {
        Lesson lesson = new Lesson();
        lesson.setModule(module);
        lesson.setTitle(title);
        return repository.save(lesson);
    }

    private void createAssignment(AssignmentRepository repository, Lesson lesson, String title) {
        Assignment assignment = new Assignment();
        assignment.setLesson(lesson);
        assignment.setTitle(title);
        repository.save(assignment);
    }

    private void enrollStudent(EnrollmentRepository repository, Course course, User student) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        repository.save(enrollment);
    }

    private void createQuizzes(
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            AnswerOptionRepository answerOptionRepository
    ) {
        createQuizWithQuestions(
            quizRepository, questionRepository, answerOptionRepository,
            "Тест: Основы Spring Framework",
            new QuizQuestion[]{
                new QuizQuestion(
                    "Что означает IoC в Spring?",
                    QuestionType.SINGLE_CHOICE,
                    new Answer[]{
                        new Answer("Inversion of Control", true),
                        new Answer("Input of Control", false),
                        new Answer("Integration of Components", false)
                    }
                ),
                new QuizQuestion(
                    "Что такое Dependency Injection?",
                    QuestionType.SINGLE_CHOICE,
                    new Answer[]{
                        new Answer("Паттерн внедрения зависимостей", true),
                        new Answer("Метод инъекции кода", false)
                    }
                )
            }
        );
        
        createQuizWithQuestions(
            quizRepository, questionRepository, answerOptionRepository,
            "Тест: Spring Boot",
            new QuizQuestion[]{
                new QuizQuestion(
                    "Какой аннотацией отмечается главный класс Spring Boot приложения?",
                    QuestionType.SINGLE_CHOICE,
                    new Answer[]{
                        new Answer("@SpringBootApplication", true),
                        new Answer("@SpringApplication", false)
                    }
                )
            }
        );
    }

    private void createQuizWithQuestions(
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            AnswerOptionRepository answerOptionRepository,
            String quizTitle,
            QuizQuestion[] questions
    ) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizTitle);
        quiz = quizRepository.save(quiz);
        
        for (QuizQuestion quizQuestion : questions) {
            Question question = new Question();
            question.setQuiz(quiz);
            question.setText(quizQuestion.text());
            question.setType(quizQuestion.type());
            question = questionRepository.save(question);
            
            for (Answer answer : quizQuestion.answers()) {
                createAnswerOption(answerOptionRepository, question, answer.text(), answer.isCorrect());
            }
        }
    }

    private void createAnswerOption(
            AnswerOptionRepository repository,
            Question question,
            String text,
            boolean isCorrect
    ) {
        AnswerOption option = new AnswerOption();
        option.setQuestion(question);
        option.setText(text);
        option.setCorrect(isCorrect);
        repository.save(option);
    }
    
    // Вспомогательные record-классы для структурирования данных тестов
    private record QuizQuestion(String text, QuestionType type, Answer[] answers) {}
    private record Answer(String text, boolean isCorrect) {}
}
