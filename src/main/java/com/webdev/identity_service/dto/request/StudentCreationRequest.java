package com.webdev.identity_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentCreationRequest {
    @NotBlank(message = "USER_ID_REQUIRED")
    String userId; // ID của User đã tạo

    @NotBlank(message = "STUDENT_CODE_REQUIRED")
    String studentCode;
    
    String firstName;
    String lastName;
    
    @Email(message = "INVALID_EMAIL")
    String email;
    
    LocalDate dob;
    String major;
}
