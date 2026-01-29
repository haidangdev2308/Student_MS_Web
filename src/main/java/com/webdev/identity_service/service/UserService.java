package com.webdev.identity_service.service;

import com.webdev.identity_service.dto.request.UserCreationRequest;
import com.webdev.identity_service.dto.request.UserUpdateRequest;
import com.webdev.identity_service.entity.User;
import com.webdev.identity_service.exception.AppException;
import com.webdev.identity_service.exception.ErrorCode;
import com.webdev.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//Tóm tắt mô hình chuẩn:(bảo mật v kiểm soát)
//  1. **Frontend** gửi JSON (`username`, `password`).
//  2. **Controller** dùng **DTO Request** (`UserCreationRequest`) để hứng và kiểm tra lỗi.
//  3. **Service** chuyển đổi (Map) từ **DTO** sang **Entity** (`User`), tự set thêm các trường ẩn (như `role`, `created_at`).
//  4. **Repository** lưu **Entity** xuống Database.

@Service //layer phụ trách xử lý logic
public class UserService {
    @Autowired //bing voi repo
    private UserRepository userRepository;

    //method tạo user, (Map) từ **DTO** sang **Entity** (`User`)
    public User createUser(UserCreationRequest request){
        User user = new User();

        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        //get từ request vào bảng user
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user); //tạo 1 row trong bảng user ở database
    }

    public User updateUser(String userId, UserUpdateRequest request){
        User user = getUser(userId);

        //get từ request vào bảng user
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user); //tạo 1 row trong bảng user ở database
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsers() {
        return userRepository.findAll(); //tat ca user
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }



}
