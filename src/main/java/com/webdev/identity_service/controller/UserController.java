package com.webdev.identity_service.controller;

import com.webdev.identity_service.dto.request.ApiResponse;
import com.webdev.identity_service.dto.request.UserCreationRequest;
import com.webdev.identity_service.dto.request.UserUpdateRequest;
import com.webdev.identity_service.dto.response.UserResponse;
import com.webdev.identity_service.entity.User;
import com.webdev.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor //tự dộng bean với dependency vào contructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping //endpoint users, dang ky user moi
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){ // map req body vao object dto, co valid
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;//chuan hoa api
    }

    @PutMapping("/{userId}")//update thong tin user
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}") //xóa dòng user
    ApiResponse<String> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("User has been deleted") // Trả về thông báo trong result
                .build();
    }

    @GetMapping//lay list user
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();// lay thong tin tu security

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")//lay thong tin user bang id
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        // Wrap kết quả từ service vào ApiResponse
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }
}
