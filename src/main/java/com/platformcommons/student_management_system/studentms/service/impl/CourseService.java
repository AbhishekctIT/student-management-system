package com.platformcommons.student_management_system.studentms.service.impl;

import com.platformcommons.student_management_system.studentms.dto.request.CourseRequest;
import com.platformcommons.student_management_system.studentms.dto.response.CourseResponse;
import com.platformcommons.student_management_system.studentms.entity.Course;
import com.platformcommons.student_management_system.studentms.exception.DuplicateResourceException;
import com.platformcommons.student_management_system.studentms.exception.ResourceNotFoundException;
import com.platformcommons.student_management_system.studentms.mapper.CourseMapper;
import com.platformcommons.student_management_system.studentms.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService implements com.platformcommons.student_management_system.studentms.service.CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    @Transactional
    public CourseResponse createCourse(CourseRequest request) {
        log.info("Creating new course: {}", request.getCourseName());

        // Check for duplicate course name
        if (courseRepository.existsByCourseNameIgnoreCase(request.getCourseName())) {
            throw new DuplicateResourceException("Course already exists: " + request.getCourseName());
        }

        Course course = courseMapper.toEntity(request);
        Course savedCourse = courseRepository.save(course);
        log.info("Course created successfully with ID: {}", savedCourse.getId());

        return courseMapper.toResponse(savedCourse);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        log.debug("Fetching course by ID: {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

        return courseMapper.toResponse(course);
    }

    @Override
    public CourseResponse getCourseByName(String name) {
        log.debug("Fetching course by name: {}", name);

        Course course = courseRepository.findByCourseNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "name", name));

        return courseMapper.toResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        log.debug("Fetching all courses");

        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        log.info("Updating course with ID: {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

        // Check duplicate course name if changed
        if (!course.getCourseName().equalsIgnoreCase(request.getCourseName()) &&
                courseRepository.existsByCourseNameIgnoreCase(request.getCourseName())) {
            throw new DuplicateResourceException("Course already exists: " + request.getCourseName());
        }

        courseMapper.updateCourseFromRequest(course, request);
        Course updatedCourse = courseRepository.save(course);

        log.info("Course updated successfully: {}", updatedCourse.getCourseName());

        return courseMapper.toResponse(updatedCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        log.info("Deleting course with ID: {}", id);

        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course", "id", id);
        }

        courseRepository.deleteById(id);
        log.info("Course deleted successfully");
    }

    @Override
    public List<CourseResponse> searchCoursesByTopic(String topic) {
        log.debug("Searching courses by topic: {}", topic);

        List<Course> courses = courseRepository.findByTopicsContaining(topic);
        return courses.stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

}
