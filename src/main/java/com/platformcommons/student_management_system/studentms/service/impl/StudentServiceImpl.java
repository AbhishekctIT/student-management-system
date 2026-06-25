package com.platformcommons.student_management_system.studentms.service.impl;

import com.platformcommons.student_management_system.studentms.dto.request.StudentRequest;
import com.platformcommons.student_management_system.studentms.dto.request.StudentUpdateRequest;
import com.platformcommons.student_management_system.studentms.dto.response.StudentResponse;
import com.platformcommons.student_management_system.studentms.entity.Address;
import com.platformcommons.student_management_system.studentms.entity.Course;
import com.platformcommons.student_management_system.studentms.entity.Student;
import com.platformcommons.student_management_system.studentms.exception.DuplicateResourceException;
import com.platformcommons.student_management_system.studentms.exception.ResourceNotFoundException;
import com.platformcommons.student_management_system.studentms.mapper.StudentMapper;
import com.platformcommons.student_management_system.studentms.repository.CourseRepository;
import com.platformcommons.student_management_system.studentms.repository.StudentRepository;
import com.platformcommons.student_management_system.studentms.service.StudentService;
import com.platformcommons.student_management_system.studentms.util.StudentCodeGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;
    private final StudentCodeGenerator codeGenerator;

    @Override
    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        log.info("Creating new student: {}", request.getName());

        // Generate unique student code
        String studentCode = codeGenerator.generateStudentCode();

        // Check for duplicate email
        if (request.getEmail() != null && studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + request.getEmail());
        }

        Student student = studentMapper.toEntity(request);
        student.setStudentCode(studentCode);

        // Set the bidirectional relationship for addresses
        if (student.getAddresses() != null) {
            student.getAddresses().forEach(address -> address.setStudent(student));
        }

        Student savedStudent = studentRepository.save(student);
        log.info("Student created successfully with code: {}", savedStudent.getStudentCode());

        return studentMapper.toResponse(savedStudent);
    }

    @Override
    public StudentResponse getStudentByCode(String studentCode) {
        log.debug("Fetching student by code: {}", studentCode);

        Student student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "studentCode", studentCode));

        return studentMapper.toResponse(student);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public StudentResponse getStudentByCodeAndDob(String studentCode, LocalDate dateOfBirth) {
        log.debug("Verifying student with code: {} and DOB: {}", studentCode, dateOfBirth);

        Student student = studentRepository.findByStudentCodeAndDateOfBirth(studentCode, dateOfBirth)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "studentCode/DOB", studentCode));

        return studentMapper.toResponse(student);
    }

    @Override
    public List<StudentResponse> getStudentsByName(String name) {
        log.debug("Searching students by name: {}", name);

        List<Student> students = studentRepository.searchStudents(name);
        return students.stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponse> getStudentsByCourse(Long courseId) {
        log.debug("Fetching students for course ID: {}", courseId);

        // Verify course exists
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course", "id", courseId);
        }

        List<Student> students = studentRepository.findStudentsByCourseId(courseId);
        return students.stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(String studentCode, StudentUpdateRequest request) {
        log.info("Updating student with code: {}", studentCode);

        Student student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "studentCode", studentCode));

        // Update basic fields
        studentMapper.updateStudentFromRequest(student, request);

        // Update addresses if provided
        if (request.getAddresses() != null) {
            // Clear existing addresses
            student.getAddresses().clear();

            // Add new addresses
            request.getAddresses().forEach(addrRequest -> {
                Address address = studentMapper.toAddressEntity(addrRequest, student);
                student.getAddresses().add(address);
            });
        }

        Student updatedStudent = studentRepository.save(student);
        log.info("Student updated successfully: {}", updatedStudent.getStudentCode());

        return studentMapper.toResponse(updatedStudent);
    }

    @Override
    @Transactional
    public void assignCourseToStudent(String studentCode, Long courseId) {
        log.info("Assigning course {} to student {}", courseId, studentCode);

        Student student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "studentCode", studentCode));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        // Check if course already assigned
        if (student.getCourses().contains(course)) {
            throw new DuplicateResourceException("Course already assigned to this student");
        }

        student.getCourses().add(course);
        studentRepository.save(student);

        log.info("Course assigned successfully");
    }

    @Override
    @Transactional
    public void removeCourseFromStudent(String studentCode, Long courseId) {
        log.info("Removing course {} from student {}", courseId, studentCode);

        Student student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "studentCode", studentCode));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        if (!student.getCourses().contains(course)) {
            throw new ResourceNotFoundException("Course not assigned to this student");
        }

        student.getCourses().remove(course);
        studentRepository.save(student);

        log.info("Course removed successfully");
    }

    @Override
    public boolean verifyStudent(String studentCode, LocalDate dateOfBirth) {
        log.debug("Verifying student: {} with DOB: {}", studentCode, dateOfBirth);

        return studentRepository.findByStudentCodeAndDateOfBirth(studentCode, dateOfBirth).isPresent();
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        log.debug("Fetching all students");

//        List<Student> students = studentRepository.findAll();
        List<Student> students = studentRepository.findAllWithDetails();
        return students.stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Student getStudentEntityByCode(String studentCode) {
        return studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "studentCode", studentCode));
    }
}
