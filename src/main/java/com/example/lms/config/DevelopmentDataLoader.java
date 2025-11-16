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
    CommandLineRunner initializeDemoData(UserRepository userRepository, 
                                        CategoryRepository categoryRepository, 
                                        CourseRepository courseRepository,
                                        ModuleRepository moduleRepository, 
                                        LessonRepository lessonRepository, 
                                        AssignmentRepository assignmentRepository,
                                        EnrollmentRepository enrollmentRepository, 
                                        SubmissionRepository submissionRepository,
                                        QuizRepository quizRepository, 
                                        QuestionRepository questionRepository, 
                                        AnswerOptionRepository answerOptionRepository) {
        return args -> {
            // Создание пользователей
            User teacher = createTeacher(userRepository);
            User student = createStudent(userRepository);
            
            // Создание категории и курса
            Category programmingCategory = createProgrammingCategory(categoryRepository);
            Course springCourse = createSpringCourse(courseRepository, programmingCategory, teacher);
            
            // Создание модулей и учебных материалов
            createIntroductionModule(moduleRepository, lessonRepository, assignmentRepository, springCourse);
            createSpringBootModule(moduleRepository, lessonRepository, assignmentRepository, springCourse);
            createDataJpaModule(moduleRepository, lessonRepository, assignmentRepository, springCourse);
            
            // Запись студента на курс
            enrollStudent(enrollmentRepository, springCourse, student);
            
            // Создание тестов и вопросов
            createSpringBasicsQuiz(quizRepository, questionRepository, answerOptionRepository);
            createSpringBootQuiz(quizRepository, questionRepository, answerOptionRepository);
        };
    }

    private User createTeacher(UserRepository userRepository) {
        User teacher = new User();
        teacher.setName("Teacher One");
        teacher.setEmail("teacher1@example.com");
        teacher.setRole(UserRole.TEACHER);
        return userRepository.save(teacher);
    }

    private User createStudent(UserRepository userRepository) {
        User student = new User();
        student.setName("Student One");
        student.setEmail("student1@example.com");
        student.setRole(UserRole.STUDENT);
        return userRepository.save(student);
    }

    private Category createProgrammingCategory(CategoryRepository categoryRepository) {
        Category category = new Category();
        category.setName("Programming");
        return categoryRepository.save(category);
    }

    private Course createSpringCourse(CourseRepository courseRepository, Category category, User teacher) {
        Course course = new Course();
        course.setTitle("Основы Spring");
        course.setDescription("Изучение Spring Framework и Spring Boot для создания современных Java-приложений");
        course.setCategory(category);
        course.setTeacher(teacher);
        course.setStartDate(LocalDate.now());
        return courseRepository.save(course);
    }

    private void createIntroductionModule(ModuleRepository moduleRepository, LessonRepository lessonRepository, 
                                        AssignmentRepository assignmentRepository, Course course) {
        Module introModule = new Module();
        introModule.setCourse(course);
        introModule.setTitle("Введение в Spring Framework");
        introModule.setOrderIndex(1);
        introModule = moduleRepository.save(introModule);

        Lesson introLesson = createLesson(lessonRepository, introModule, "Введение в Spring");
        Lesson diLesson = createLesson(lessonRepository, introModule, "Dependency Injection и IoC");

        createAssignment(assignmentRepository, introLesson, "ДЗ: Настройка первого Spring приложения");
        createAssignment(assignmentRepository, diLesson, "ДЗ: Практика с Dependency Injection");
    }

    private void createSpringBootModule(ModuleRepository moduleRepository, LessonRepository lessonRepository,
                                      AssignmentRepository assignmentRepository, Course course) {
        Module bootModule = new Module();
        bootModule.setCourse(course);
        bootModule.setTitle("Spring Boot");
        bootModule.setOrderIndex(2);
        bootModule = moduleRepository.save(bootModule);

        Lesson bootBasicsLesson = createLesson(lessonRepository, bootModule, "Основы Spring Boot");
        Lesson autoConfigLesson = createLesson(lessonRepository, bootModule, "Spring Boot Auto-Configuration");

        createAssignment(assignmentRepository, bootBasicsLesson, "ДЗ: Создание REST API с Spring Boot");
    }

    private void createDataJpaModule(ModuleRepository moduleRepository, LessonRepository lessonRepository,
                                   AssignmentRepository assignmentRepository, Course course) {
        Module dataModule = new Module();
        dataModule.setCourse(course);
        dataModule.setTitle("Spring Data JPA");
        dataModule.setOrderIndex(3);
        dataModule = moduleRepository.save(dataModule);

        Lesson jpaLesson = createLesson(lessonRepository, dataModule, "Работа с Spring Data JPA");
        createAssignment(assignmentRepository, jpaLesson, "ДЗ: Создание репозиториев и сущностей");
    }

    private Lesson createLesson(LessonRepository lessonRepository, Module module, String title) {
        Lesson lesson = new Lesson();
        lesson.setModule(module);
        lesson.setTitle(title);
        return lessonRepository.save(lesson);
    }

    private void createAssignment(AssignmentRepository assignmentRepository, Lesson lesson, String title) {
        Assignment assignment = new Assignment();
        assignment.setLesson(lesson);
        assignment.setTitle(title);
        assignmentRepository.save(assignment);
    }

    private void enrollStudent(EnrollmentRepository enrollmentRepository, Course course, User student) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollmentRepository.save(enrollment);
    }

    private void createSpringBasicsQuiz(QuizRepository quizRepository, QuestionRepository questionRepository,
                                      AnswerOptionRepository answerOptionRepository) {
        Quiz springBasicsQuiz = new Quiz();
        springBasicsQuiz.setTitle("Тест: Основы Spring Framework");
        springBasicsQuiz = quizRepository.save(springBasicsQuiz);

        createIoCQuestion(questionRepository, answerOptionRepository, springBasicsQuiz);
        createDIQuestion(questionRepository, answerOptionRepository, springBasicsQuiz);
    }

    private void createSpringBootQuiz(QuizRepository quizRepository, QuestionRepository questionRepository,
                                    AnswerOptionRepository answerOptionRepository) {
        Quiz bootQuiz = new Quiz();
        bootQuiz.setTitle("Тест: Spring Boot");
        bootQuiz = quizRepository.save(bootQuiz);

        createBootAnnotationQuestion(questionRepository, answerOptionRepository, bootQuiz);
    }

    private void createIoCQuestion(QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository,
                                 Quiz quiz) {
        Question question = new Question();
        question.setQuiz(quiz);
        question.setText("Что означает IoC в Spring?");
        question.setType(QuestionType.SINGLE_CHOICE);
        question = questionRepository.save(question);

        createAnswerOption(answerOptionRepository, question, "Inversion of Control", true);
        createAnswerOption(answerOptionRepository, question, "Input of Control", false);
        createAnswerOption(answerOptionRepository, question, "Integration of Components", false);
    }

    private void createDIQuestion(QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository,
                                Quiz quiz) {
        Question question = new Question();
        question.setQuiz(quiz);
        question.setText("Что такое Dependency Injection?");
        question.setType(QuestionType.SINGLE_CHOICE);
        question = questionRepository.save(question);

        createAnswerOption(answerOptionRepository, question, "Паттерн внедрения зависимостей", true);
        createAnswerOption(answerOptionRepository, question, "Метод инъекции кода", false);
    }

    private void createBootAnnotationQuestion(QuestionRepository questionRepository, 
                                           AnswerOptionRepository answerOptionRepository, Quiz quiz) {
        Question question = new Question();
        question.setQuiz(quiz);
        question.setText("Какой аннотацией отмечается главный класс Spring Boot приложения?");
        question.setType(QuestionType.SINGLE_CHOICE);
        question = questionRepository.save(question);

        createAnswerOption(answerOptionRepository, question, "@SpringBootApplication", true);
        createAnswerOption(answerOptionRepository, question, "@SpringApplication", false);
    }

    private void createAnswerOption(AnswerOptionRepository answerOptionRepository, Question question, 
                                  String text, boolean isCorrect) {
        AnswerOption option = new AnswerOption();
        option.setQuestion(question);
        option.setText(text);
        option.setCorrect(isCorrect);
        answerOptionRepository.save(option);
    }
}
