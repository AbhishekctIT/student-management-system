package com.platformcommons.student_management_system.studentms.service;

import com.platformcommons.student_management_system.studentms.dto.request.StudentRequest;
import com.platformcommons.student_management_system.studentms.dto.request.StudentUpdateRequest;
import com.platformcommons.student_management_system.studentms.dto.response.StudentResponse;
import com.platformcommons.student_management_system.studentms.entity.Student;

import java.time.LocalDate;
import java.util.List;

public interface StudentService {
    StudentResponse createStudent(StudentRequest request);

    StudentResponse getStudentByCode(String studentCode);

    StudentResponse getStudentByCodeAndDob(String studentCode, LocalDate dateOfBirth);

    List<StudentResponse> getStudentsByName(String name);

    List<StudentResponse> getStudentsByCourse(Long courseId);

    StudentResponse updateStudent(String studentCode, StudentUpdateRequest request);

    void assignCourseToStudent(String studentCode, Long courseId);

    void removeCourseFromStudent(String studentCode, Long courseId);

    boolean verifyStudent(String studentCode, LocalDate dateOfBirth);

    List<StudentResponse> getAllStudents();

    Student getStudentEntityByCode(String studentCode);
}
