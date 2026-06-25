package com.platformcommons.student_management_system.studentms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Course extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "course_type", length = 50)
    private String courseType;

    @Column(length = 50)
    private String duration;

    @Column(columnDefinition = "TEXT")
    private String topics;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
}

