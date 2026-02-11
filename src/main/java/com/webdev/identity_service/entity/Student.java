package com.webdev.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "user_id", unique = true)
    String userId; // Liên kết với bảng User

    @Column(unique = true, nullable = false)
    String studentCode;

    String firstName;
    String lastName;
    String email;
    LocalDate dob;
    String major;
    Double gpa;
}
