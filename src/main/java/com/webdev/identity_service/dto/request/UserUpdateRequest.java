package com.webdev.identity_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

//(Data Transfer Object)
@Data //tu dong tao getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //giúp bạn tạo ra một đối tượng (Object) dễ hiểu
@FieldDefaults(level = AccessLevel.PRIVATE) // mặc đinh field là private
public class UserUpdateRequest {
    //id tự tạo khi insert 1 dòng vào table
     String password;
     String firstName;
     String lastName;
     LocalDate dob;
     List<String> roles;
}
