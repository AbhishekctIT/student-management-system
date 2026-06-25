package com.platformcommons.student_management_system.studentms.service;

import com.platformcommons.student_management_system.studentms.dto.request.CourseRequest;
import com.platformcommons.student_management_system.studentms.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CourseRequest request);

    CourseResponse getCourseById(Long id);

    CourseResponse getCourseByName(String name);

    List<CourseResponse> getAllCourses();

    CourseResponse updateCourse(Long id, CourseRequest request);

    void deleteCourse(Long id);

    List<CourseResponse> searchCoursesByTopic(String topic);
}
