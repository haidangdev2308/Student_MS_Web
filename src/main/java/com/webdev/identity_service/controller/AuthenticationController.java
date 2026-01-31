package com.webdev.identity_service.controller;

import com.nimbusds.jose.JOSEException;
import com.webdev.identity_service.dto.request.ApiResponse;
import com.webdev.identity_service.dto.request.AuthenticationRequest;
import com.webdev.identity_service.dto.request.IntrospectRequest;
import com.webdev.identity_service.dto.response.AuthenticationResponse;
import com.webdev.identity_service.dto.response.IntrospectResponse;
import com.webdev.identity_service.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // autowire cac ping
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")//log-in
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();//build ra response bang builder
    }

    @PostMapping("/introspect")//verify token
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();//build ra response bang builder
    }
}
