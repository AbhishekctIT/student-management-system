# student-management-system

A REST API for managing student admissions, courses, and enrollments built with Spring Boot.

## Features

### Admin Operations
- Student Admission with multiple addresses (permanent, correspondence, current)
- Course Management (CRUD operations)
- Course Assignment to students
- Search students by name
- Search students by course

### Student Operations
- Profile Management (email, mobile, parents' names, addresses)
- Course Search by topics
- Leave course

### Security
- JWT-based authentication
- Admin login
- Student verification using student_code and date_of_birth

## Technologies Used

- Java 17
- Spring Boot 3.1.5
- Spring Security with JWT
- Spring Data JPA with Hibernate
- MySQL Database
- MapStruct for DTO mapping
- Swagger/OpenAPI for documentation
- JUnit 5 and Mockito for testing
- Flyway for database migrations

## Setup Instructions

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.8 or higher
- Postman (for API testing)

### Database Setup
1. Create a MySQL database named `student_db`
2. Run the application - Flyway will create the tables automatically

### Application Configuration
1. Update `application.yml` with your database credentials:
   ```yaml
   datasource:
     url: jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC
     username: your_username
     password: your_password
