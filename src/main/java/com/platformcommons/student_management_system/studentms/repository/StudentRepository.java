package com.platformcommons.student_management_system.studentms.repository;

import com.platformcommons.student_management_system.studentms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses LEFT JOIN FETCH s.addresses WHERE s.studentCode = :studentCode AND s.dateOfBirth = :dateOfBirth")
    Optional<Student> findByStudentCodeAndDateOfBirth(@Param("studentCode") String studentCode, @Param("dateOfBirth") LocalDate dateOfBirth);

    Optional<Student> findByStudentCode(String studentCode);

//    Optional<Student> findByStudentCodeAndDateOfBirth(String studentCode, LocalDate dateOfBirth);

    List<Student> findByNameContainingIgnoreCase(String name);
    @Query("""
SELECT DISTINCT s
FROM Student s
LEFT JOIN FETCH s.addresses
LEFT JOIN FETCH s.courses
WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))
""")
    List<Student> searchStudents(@Param("name") String name);

    @Query("""
       SELECT DISTINCT s
       FROM Student s
       LEFT JOIN FETCH s.addresses
       LEFT JOIN FETCH s.courses c
       WHERE c.id = :courseId
       """)
    List<Student> findStudentsByCourseId(@Param("courseId") Long courseId);

    boolean existsByStudentCode(String studentCode);

    boolean existsByEmail(String email);

    Optional<Student> findByEmail(String email);

    Optional<Student> findTopByOrderByIdDesc();

    @Query("""
       SELECT DISTINCT s
       FROM Student s
       LEFT JOIN FETCH s.addresses
       LEFT JOIN FETCH s.courses
       """)
    List<Student> findAllWithDetails();
}
