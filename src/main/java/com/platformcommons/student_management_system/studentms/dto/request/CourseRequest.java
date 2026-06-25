package com.platformcommons.student_management_system.studentms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {

    @NotBlank
    @Size(max = 100)
    private String courseName;

    private String description;

    private String courseType;

    private String duration;

    private String topics;
}
