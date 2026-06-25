package com.platformcommons.student_management_system.studentms.repository;

import com.platformcommons.student_management_system.studentms.entity.Address;
import com.platformcommons.student_management_system.studentms.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

//    Optional<Admin> findByUsername(String username);
//
//    Optional<Admin> findByEmail(String email);

    // Find addresses by student code
    @Query("SELECT a FROM Address a WHERE a.student.studentCode = :studentCode")
    List<Address> findByStudentCode(@Param("studentCode") String studentCode);

    // Find addresses by student ID
    List<Address> findByStudentId(Long studentId);

}
