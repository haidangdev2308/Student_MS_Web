package com.webdev.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data //tu dong tao getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //giúp bạn tạo ra một đối tượng (Object) dễ hiểu
@FieldDefaults(level = AccessLevel.PRIVATE) // mặc đinh field là private
public class UserResponse { // dto response từ service trả về controller
    String id;
    String username;
    String firstName;
    String lastName;
    String email;
    LocalDate dob;
    Set<RoleResponse> roles;//Set đảm bảo mỗi pt là unit, khác List
}
