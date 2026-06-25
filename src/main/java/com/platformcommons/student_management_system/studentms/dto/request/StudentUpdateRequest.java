package com.platformcommons.student_management_system.studentms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequest {

    @Email
    private String email;

    @Size(max = 15)
    private String mobileNumber;

    @Size(max = 200)
    private String parentsName;

    private List<AddressRequest> addresses;
}
