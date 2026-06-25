package com.platformcommons.student_management_system.studentms.controller;

import com.platformcommons.student_management_system.studentms.dto.request.StudentUpdateRequest;
import com.platformcommons.student_management_system.studentms.dto.request.StudentVerificationRequest;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student Operations", description = "Student-facing APIs")
@SecurityRequirement(name = "bearerAuth")
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;

    @PostMapping("/verify")
    @Operation(summary = "Verify student", description = "Verify student identity using student_code and DOB")
    public ResponseEntity<ApiResponse<StudentResponse>> verifyStudent(
            @Valid @RequestBody StudentVerificationRequest request) {
        StudentResponse response = studentService.getStudentByCodeAndDob(
                request.getStudentCode(), request.getDateOfBirth());
        return ResponseEntity.ok(ApiResponse.success("Student verified successfully", response));
    }

    @GetMapping("/{studentCode}")
    @Operation(summary = "Get student profile", description = "Get student details by student_code")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentProfile(@PathVariable String studentCode) {
        StudentResponse response = studentService.getStudentByCode(studentCode);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{studentCode}")
    @Operation(summary = "Update profile", description = "Update student profile details")
    public ResponseEntity<ApiResponse<StudentResponse>> updateProfile(
            @PathVariable String studentCode,
            @Valid @RequestBody StudentUpdateRequest request) {
        StudentResponse response = studentService.updateStudent(studentCode, request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", response));
    }

    @GetMapping("/{studentCode}/courses")
    @Operation(summary = "Get student courses", description = "Get all courses assigned to a student")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getStudentCourses(@PathVariable String studentCode) {
        StudentResponse student = studentService.getStudentByCode(studentCode);
        List<CourseResponse> courses = student.getCourses() != null ?
                student.getCourses().stream().toList() :
                List.of();
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @GetMapping("/courses/search")
    @Operation(summary = "Search courses by topic", description = "Search for courses by topic")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> searchCourses(@RequestParam String topic) {
        List<CourseResponse> courses = courseService.searchCoursesByTopic(topic);
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @DeleteMapping("/{studentCode}/courses/{courseId}")
    @Operation(summary = "Leave course", description = "Student leaves a course")
    public ResponseEntity<ApiResponse<Void>> leaveCourse(
            @PathVariable String studentCode,
            @PathVariable Long courseId) {
        studentService.removeCourseFromStudent(studentCode, courseId);
        return ResponseEntity.ok(ApiResponse.success("You have left the course successfully", null));
    }
}
