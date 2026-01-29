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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor //tự dộng bing với dependency vào contructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping //endpoint users
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){ // map req body vao object dto, co valid
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;//chuan hoa api
    }

    @PutMapping("/{userId}") //dau ngoac de khai bao bien tren path
    UserResponse updateUser(@RequestBody UserUpdateRequest request, @PathVariable("userId") String userId) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "User has been deleted";
    }

    @GetMapping
    List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}") //dau nguoc de khai bao bien tren path
    UserResponse getUser(@PathVariable("userId") String userId) {
            return userService.getUser(userId);
    }
}
