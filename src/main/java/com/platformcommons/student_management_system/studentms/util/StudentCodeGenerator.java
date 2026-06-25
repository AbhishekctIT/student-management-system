package com.platformcommons.student_management_system.studentms.util;

import com.platformcommons.student_management_system.studentms.entity.Student;
import com.platformcommons.student_management_system.studentms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class StudentCodeGenerator {
    private final StudentRepository studentRepository;

    public String generateStudentCode() {
        String prefix = "STU";
        String year = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String sequence = "001";
        String lastCode = studentRepository.findTopByOrderByIdDesc()
                .map(Student::getStudentCode)
                .orElse(null);
        if (lastCode != null && lastCode.startsWith(prefix + year)) {
            try {
                int lastSeq = Integer.parseInt(lastCode.substring(7));
                sequence = String.format("%03d", lastSeq + 1);
            } catch (Exception e) {
                sequence = "001";
            }
        }
        return prefix + year + sequence;
    }
}
