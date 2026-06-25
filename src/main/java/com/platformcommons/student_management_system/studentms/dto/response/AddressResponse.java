package com.platformcommons.student_management_system.studentms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private Long id;
    private String addressType;
    private String street;
    private String city;
    private String state;
    private String pincode;
    private String country;
}
