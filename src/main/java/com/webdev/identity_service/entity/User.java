package com.webdev.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

//table User
@Entity
@Data //tu dong tao getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //giúp bạn tạo ra một đối tượng (Object) dễ hiểu
@FieldDefaults(level = AccessLevel.PRIVATE) // mặc đinh field là private
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //theo uuid chuỗi không trùng lặp
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    LocalDate dob;

    @ManyToMany //qqh nhiều nhiều với role
    Set<Role> roles;//Set đảm bảo mỗi pt là unit, khác List
}
