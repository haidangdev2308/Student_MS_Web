package com.webdev.identity_service.service;

import com.webdev.identity_service.dto.request.AuthenticationRequest;
import com.webdev.identity_service.exception.AppException;
import com.webdev.identity_service.exception.ErrorCode;
import com.webdev.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service //layer phụ trách xử lý logic
@RequiredArgsConstructor //tự dộng bing với dependency vào contructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    public boolean authenticate(AuthenticationRequest request) { // method xac thuc
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));// tim user xem co khop voi username vua nhap khong

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10); //ma hoa bcript
        return passwordEncoder.matches(request.getPassword(), user.getPassword());//so pw tu request voi pw cua user vua tim duoc

    }
}
