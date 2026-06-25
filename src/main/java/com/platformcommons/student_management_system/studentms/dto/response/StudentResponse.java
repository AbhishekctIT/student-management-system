package com.platformcommons.student_management_system.studentms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private Long id;
    private String studentCode;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String mobileNumber;
    private String parentsName;
    private List<AddressResponse> addresses;
    private Set<CourseResponse> courses;
}
