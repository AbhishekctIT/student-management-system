package com.platformcommons.student_management_system.studentms.service;

import com.platformcommons.student_management_system.studentms.dto.request.StudentRequest;
import com.platformcommons.student_management_system.studentms.dto.response.StudentResponse;
import com.platformcommons.student_management_system.studentms.entity.Student;
import com.platformcommons.student_management_system.studentms.repository.StudentRepository;
import com.platformcommons.student_management_system.studentms.service.impl.StudentServiceImpl;
import com.platformcommons.student_management_system.studentms.util.StudentCodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentCodeGenerator studentCodeGenerator;

    @InjectMocks
    private StudentServiceImpl studentService;

    private StudentRequest studentRequest;
    private Student student;

    @BeforeEach
    void setUp() {
        studentRequest = StudentRequest.builder()
                .name("Abhishek")
                .dateOfBirth(LocalDate.of(2000, 1, 15))
                .gender("Male")
                .email("abhi@example.com")
                .mobileNumber("9876543210")
                .parentsName("Mr. and Mrs. Parents")
                .build();
        student = Student.builder()
                .id(1L)
                .studentCode("STU2024001")
                .name("Abhishek")
                .dateOfBirth(LocalDate.of(2000, 1, 15))
                .gender("Male")
                .email("abhi@example.com")
                .mobileNumber("9876543210")
                .parentsName("Mr. and Mrs. Parents")
                .build();

    }

    @Test
    void createStudent_ShouldReturnStudentResponse() {
        when(studentCodeGenerator.generateStudentCode()).thenReturn("STU2024001");
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        StudentResponse response = studentService.createStudent(studentRequest);
        assertNotNull(response);
        assertEquals("Abhishek", response.getName());
        assertEquals("STU2024001", response.getStudentCode());
        verify(studentRepository, times(1)).findByStudentCode("STU2024001");
    }

    @Test
    void getStudentByCode_ShouldReturnStudent() {
        when(studentRepository.findByStudentCode("STU2024001")).thenReturn(Optional.of(student));

        StudentResponse response = studentService.getStudentByCode("STU2024001");

        assertNotNull(response);
        assertEquals("Abhishek", response.getName());
        verify(studentRepository, times(1)).findByStudentCode("STU2024001");
    }

    @Test
    void getStudentByCode_WhenNotFound_ShouldThrowException() {
        when(studentRepository.findByStudentCode("INVALID")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            studentService.getStudentByCode("INVALID");
        });
    }
}
