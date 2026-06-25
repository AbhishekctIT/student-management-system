package com.platformcommons.student_management_system.studentms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    private Long id;
    private String courseName;
    private String description;
    private String courseType;
    private String duration;
    private String topics;
    private Integer studentCount;
}
