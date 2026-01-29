package com.webdev.identity_service.controller;

import com.webdev.identity_service.dto.request.ApiResponse;
import com.webdev.identity_service.dto.request.UserCreationRequest;
import com.webdev.identity_service.dto.request.UserUpdateRequest;
import com.webdev.identity_service.entity.User;
import com.webdev.identity_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired //tuong tac voi service
    private UserService userService;

    @PostMapping //endpoint users
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){ // map req body vao object dto, co valid
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;//chuan hoa api
    }

    @PutMapping("/{userId}") //dau ngoac de khai bao bien tren path
    User updateUser(@RequestBody UserUpdateRequest request, @PathVariable("userId") String userId) {
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
    User getUser(@PathVariable("userId") String userId) {
            return userService.getUser(userId);
    }
}
