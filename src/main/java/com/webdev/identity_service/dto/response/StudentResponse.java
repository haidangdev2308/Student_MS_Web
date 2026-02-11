package com.webdev.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
    String id;
    String userId;
    String studentCode;
    String firstName;
    String lastName;
    String email;
    LocalDate dob;
    String major;
    Double gpa;
}
