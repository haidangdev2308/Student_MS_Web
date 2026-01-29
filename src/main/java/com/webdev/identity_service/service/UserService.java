package com.webdev.identity_service.service;

import com.webdev.identity_service.dto.request.UserCreationRequest;
import com.webdev.identity_service.dto.request.UserUpdateRequest;
import com.webdev.identity_service.dto.response.UserResponse;
import com.webdev.identity_service.entity.User;
import com.webdev.identity_service.exception.AppException;
import com.webdev.identity_service.exception.ErrorCode;
import com.webdev.identity_service.mapper.UserMapper;
import com.webdev.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //method tạo user, (Map) từ **DTO** sang **Entity** (`User`)
    public User createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        //get từ request vào bảng user bằng mapper
        User user = userMapper.toUser(request);

        return userRepository.save(user); //tạo 1 row trong bảng user ở database
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        //get từ request vào bảng user bang mapper
        userMapper.updateUser(user,request);

        return userMapper.toUserResponse(userRepository.save(user)); //tạo 1 row trong bảng user ở database
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsers() {
        return userRepository.findAll(); //tat ca user
    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }



}
