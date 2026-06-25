package com.platformcommons.student_management_system.studentms.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @NotBlank
    private String gender;

    @Email
    private String email;

    @Size(max = 15)
    private String mobileNumber;

    @Size(max = 200)
    private String parentsName;

    @Valid
    private List<AddressRequest> addresses;
}
