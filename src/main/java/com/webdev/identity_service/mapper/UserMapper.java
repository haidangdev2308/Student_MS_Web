package com.webdev.identity_service.mapper;

import com.webdev.identity_service.dto.request.UserCreationRequest;
import com.webdev.identity_service.dto.request.UserUpdateRequest;
import com.webdev.identity_service.dto.response.UserResponse;
import com.webdev.identity_service.entity.Role;
import com.webdev.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring") //generate su dung trong sping
public interface UserMapper {
    User toUser(UserCreationRequest request); //method nhận parameter request kiểu UserCreationRequest để trả về class User
    void updateUser(@MappingTarget User user, UserUpdateRequest request); // map userUpdateRequest vao user target
    UserResponse toUserResponse(User user); //map từ User trả từ class user (repo) thành dto response

    // MapStruct sẽ tự động sử dụng method này khi thấy cần convert Set<Role> sang Set<String>
    default String mapRoleToString(Role role) {
        if (role == null) return null;
        return role.getName(); // Giả sử Role entity có field 'name'
    }
}
