package com.webdev.identity_service.dto.request;

import com.webdev.identity_service.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

//(Data Transfer Object)
@Data //tu dong tao getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //giúp bạn tạo ra một đối tượng (Object) dễ hiểu
@FieldDefaults(level = AccessLevel.PRIVATE) // mặc đinh field là private
public class UserCreationRequest {
    //id tự tạo khi insert 1 dòng vào table
    @Size(min = 3, message = "USERNAME_INVALID") //enum lỗi
     String username;

    @Size(min = 8, message = "INVALID_PASSWORD")
     String password;
     String firstName;
     String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
     LocalDate dob;


}
