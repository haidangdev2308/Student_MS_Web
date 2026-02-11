package com.webdev.identity_service.controller;

import com.webdev.identity_service.dto.request.ApiResponse;
import com.webdev.identity_service.dto.request.StudentCreationRequest;
import com.webdev.identity_service.dto.request.StudentSearchRequest;
import com.webdev.identity_service.dto.response.StudentResponse;
import com.webdev.identity_service.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentController {
    StudentService studentService;

    @PostMapping
    public ApiResponse<StudentResponse> createStudent(@RequestBody StudentCreationRequest request) {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.createStudent(request))
                .build();
    }

    @GetMapping
    public ApiResponse<Page<StudentResponse>> getStudents(
            @ModelAttribute StudentSearchRequest searchRequest,
            Pageable pageable) {
        return ApiResponse.<Page<StudentResponse>>builder()
                .result(studentService.searchStudents(searchRequest, pageable))
                .build();
    }
}
