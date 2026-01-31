package com.webdev.identity_service.service;

import com.webdev.identity_service.dto.request.UserCreationRequest;
import com.webdev.identity_service.dto.request.UserUpdateRequest;
import com.webdev.identity_service.dto.response.UserResponse;
import com.webdev.identity_service.entity.User;
import com.webdev.identity_service.enums.Role;
import com.webdev.identity_service.exception.AppException;
import com.webdev.identity_service.exception.ErrorCode;
import com.webdev.identity_service.mapper.UserMapper;
import com.webdev.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

//Tóm tắt mô hình chuẩn:(bảo mật v kiểm soát)
//  1. **Frontend** gửi JSON (`username`, `password`).
//  2. **Controller** dùng **DTO Request** (`UserCreationRequest`) để hứng và kiểm tra lỗi.
//  3. **Service** chuyển đổi (Map) từ **DTO** sang **Entity** (`User`), tự set thêm các trường ẩn (như `role`, `created_at`).
//  4. **Repository** lưu **Entity** xuống Database.

@Service //layer phụ trách xử lý logic
@RequiredArgsConstructor //tự dộng bing với dependency vào contructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
     UserRepository userRepository;
     UserMapper userMapper;
     PasswordEncoder passwordEncoder;

    //method tạo user, (Map) từ **DTO** sang **Entity** (`User`)
    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        //get từ request vào bảng user bằng mapper
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));//encode pw user nhap

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name()); // khi tao user moi se co them 1 list role, mac dinh co role USER
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user)); //tạo 1 row trong bảng user ở database
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        // Map dữ liệu từ request vào entity user cũ
        userMapper.updateUser(user, request);
        // Lưu và trả về response
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        // Nên kiểm tra xem user có tồn tại không trước khi xóa
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        userRepository.deleteById(userId);
    }

    // Lấy danh sách
    public List<UserResponse> getUsers() {
        // Cách 1: Nếu Mapper của bạn đã có hàm map List -> Giữ nguyên code của bạn
        // return userMapper.toUserResponseList(userRepository.findAll());

        // Cách 2: (An toàn nhất) Dùng Stream để map từng phần tử
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    // Lấy 1 user
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED))
        );
    }



}
