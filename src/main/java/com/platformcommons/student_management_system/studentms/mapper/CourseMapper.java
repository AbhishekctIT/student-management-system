package com.platformcommons.student_management_system.studentms.mapper;

import com.platformcommons.student_management_system.studentms.dto.request.CourseRequest;
import com.platformcommons.student_management_system.studentms.dto.response.CourseResponse;
import com.platformcommons.student_management_system.studentms.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequest request) {
        if (request == null) {
            return null;
        }

        return Course.builder()
                .courseName(request.getCourseName())
                .description(request.getDescription())
                .courseType(request.getCourseType())
                .duration(request.getDuration())
                .topics(request.getTopics())
                .build();
    }

    public CourseResponse toResponse(Course course) {
        if (course == null) {
            return null;
        }

        CourseResponse response = CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .courseType(course.getCourseType())
                .duration(course.getDuration())
                .topics(course.getTopics())
                .studentCount(0).build();

        return response;
    }

    public void updateCourseFromRequest(Course course, CourseRequest request) {
        if (request.getCourseName() != null) {
            course.setCourseName(request.getCourseName());
        }
        if (request.getDescription() != null) {
            course.setDescription(request.getDescription());
        }
        if (request.getCourseType() != null) {
            course.setCourseType(request.getCourseType());
        }
        if (request.getDuration() != null) {
            course.setDuration(request.getDuration());
        }
        if (request.getTopics() != null) {
            course.setTopics(request.getTopics());
        }
    }
}

