package com.platformcommons.student_management_system.studentms.mapper;

import com.platformcommons.student_management_system.studentms.dto.request.AddressRequest;
import com.platformcommons.student_management_system.studentms.dto.request.StudentRequest;
import com.platformcommons.student_management_system.studentms.dto.request.StudentUpdateRequest;
import com.platformcommons.student_management_system.studentms.dto.response.AddressResponse;
import com.platformcommons.student_management_system.studentms.dto.response.CourseResponse;
import com.platformcommons.student_management_system.studentms.dto.response.StudentResponse;
import com.platformcommons.student_management_system.studentms.entity.Address;
import com.platformcommons.student_management_system.studentms.entity.Course;
import com.platformcommons.student_management_system.studentms.entity.Student;
import org.hibernate.collection.spi.PersistentCollection;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    public Student toEntity(StudentRequest request) {
        if (request == null) {
            return null;
        }

        Student student = Student.builder()
                .name(request.getName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .parentsName(request.getParentsName())
                .build();

        if (request.getAddresses() != null) {
            List<Address> addresses = request.getAddresses().stream()
                    .map(addr -> toAddressEntity(addr, student))
                    .collect(Collectors.toList());
            student.setAddresses(addresses);
        }

        return student;
    }

    public Address toAddressEntity(AddressRequest request, Student student) {
        if (request == null) {
            return null;
        }

        Address address = Address.builder()
                .addressType(request.getAddressType())
                .street(request.getStreet())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .country(request.getCountry())
                .student(student)
                .build();

        return address;
    }

    public StudentResponse toResponse(Student student) {
        if (student == null) {
            return null;
        }

        StudentResponse response = StudentResponse.builder()
                .id(student.getId())
                .studentCode(student.getStudentCode())
                .name(student.getName())
                .dateOfBirth(student.getDateOfBirth())
                .gender(student.getGender())
                .email(student.getEmail())
                .mobileNumber(student.getMobileNumber())
                .parentsName(student.getParentsName())
                .build();

        // Map addresses
        if (student.getAddresses() != null) {
            List<AddressResponse> addressResponses = student.getAddresses().stream()
                    .map(this::toAddressResponse)
                    .collect(Collectors.toList());
            response.setAddresses(addressResponses);
        }

        // ✅ SAFE - Check if courses collection is initialized
        if (student.getCourses() != null && !isCollectionInitialized(student.getCourses())) {
            // Force initialization
            student.getCourses().size();
        }

        // Map courses
        if (student.getCourses() != null) {
            Set<CourseResponse> courseResponses = student.getCourses().stream()
                    .map(this::toCourseResponse)
                    .collect(Collectors.toSet());
            response.setCourses(courseResponses);
        }

        return response;
    }

    private boolean isCollectionInitialized(Collection<?> collection) {
        if (collection == null) {
            return true;
        }
        if (collection instanceof PersistentCollection) {
            return ((PersistentCollection<?>) collection).wasInitialized();
        }
        return true;
    }

    public AddressResponse toAddressResponse(Address address) {
        if (address == null) {
            return null;
        }

        return AddressResponse.builder()
                .id(address.getId())
                .addressType(address.getAddressType())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .pincode(address.getPincode())
                .country(address.getCountry())
                .build();
    }

    public CourseResponse toCourseResponse(Course course) {
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
                .studentCount(0)
                .build();

        return response;
    }

    public void updateStudentFromRequest(Student student, StudentUpdateRequest request) {
        if (request.getEmail() != null) {
            student.setEmail(request.getEmail());
        }
        if (request.getMobileNumber() != null) {
            student.setMobileNumber(request.getMobileNumber());
        }
        if (request.getParentsName() != null) {
            student.setParentsName(request.getParentsName());
        }
    }
}
