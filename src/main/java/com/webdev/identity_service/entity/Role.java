package com.webdev.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

//table Role
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Role
{
    @Id
    String name;
    String description;

    @ManyToMany//mqh nhiều nhiều, hipernate tu dong map permission vao role-permision
    Set<Permission> permissions;//field permission
}
