package com.platformcommons.student_management_system.studentms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentVerificationRequest {

    @NotBlank
    private String studentCode;

    @NotNull
    private LocalDate dateOfBirth;

}
