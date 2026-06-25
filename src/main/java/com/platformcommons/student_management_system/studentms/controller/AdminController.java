package com.platformcommons.student_management_system.studentms.controller;

import com.platformcommons.student_management_system.studentms.dto.request.CourseRequest;
import com.platformcommons.student_management_system.studentms.dto.request.StudentRequest;
import com.platformcommons.student_management_system.studentms.dto.response.ApiResponse;
import com.platformcommons.student_management_system.studentms.dto.response.CourseResponse;
import com.platformcommons.student_management_system.studentms.dto.response.StudentResponse;
import com.platformcommons.student_management_system.studentms.service.StudentService;
import com.platformcommons.student_management_system.studentms.service.impl.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Operations", description = "Admin-only APIs for managing students and courses")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final StudentService studentService;
    private final CourseService courseService;

    // Student Admission
    @PostMapping("/students")
    @Operation(summary = "Admit student", description = "Add a new student with address details")
    public ResponseEntity<ApiResponse<StudentResponse>> admitStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse response = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Student admitted successfully", response));
    }

    // Course Management
    @PostMapping("/courses")
    @Operation(summary = "Create course", description = "Add a new course with details")
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@Valid @RequestBody CourseRequest request) {
        CourseResponse response = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Course created successfully", response));
    }

    @PutMapping("/courses/{id}")
    @Operation(summary = "Update course", description = "Update existing course details")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request) {
        CourseResponse response = courseService.updateCourse(id, request);
        return ResponseEntity.ok(ApiResponse.success("Course updated successfully", response));
    }

    @DeleteMapping("/courses/{id}")
    @Operation(summary = "Delete course", description = "Delete a course")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(ApiResponse.success("Course deleted successfully", null));
    }

    @GetMapping("/courses")
    @Operation(summary = "Get all courses", description = "List all available courses")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses() {
        List<CourseResponse> courses = courseService.getAllCourses();
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    // Course Assignment
    @PostMapping("/students/{studentCode}/courses/{courseId}")
    @Operation(summary = "Assign course to student", description = "Assign a course to a student")
    public ResponseEntity<ApiResponse<Void>> assignCourseToStudent(
            @PathVariable String studentCode,
            @PathVariable Long courseId) {
        studentService.assignCourseToStudent(studentCode, courseId);
        return ResponseEntity.ok(ApiResponse.success("Course assigned successfully", null));
    }

    @DeleteMapping("/students/{studentCode}/courses/{courseId}")
    @Operation(summary = "Remove course from student", description = "Remove a course from a student")
    public ResponseEntity<ApiResponse<Void>> removeCourseFromStudent(
            @PathVariable String studentCode,
            @PathVariable Long courseId) {
        studentService.removeCourseFromStudent(studentCode, courseId);
        return ResponseEntity.ok(ApiResponse.success("Course removed successfully", null));
    }

    // Search Functionalities
    @GetMapping("/students/search")
    @Operation(summary = "Search students by name", description = "Get students by their name")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getStudentsByName(@RequestParam String name) {
        List<StudentResponse> students = studentService.getStudentsByName(name);
        return ResponseEntity.ok(ApiResponse.success(students));
    }

    @GetMapping("/courses/{courseId}/students")
    @Operation(summary = "Get students by course", description = "Get all students assigned to a course")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getStudentsByCourse(@PathVariable Long courseId) {
        List<StudentResponse> students = studentService.getStudentsByCourse(courseId);
        return ResponseEntity.ok(ApiResponse.success(students));
    }

    @GetMapping("/students/all")
    @Operation(summary = "Get all students", description = "List all admitted students")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success(students));
    }
}
