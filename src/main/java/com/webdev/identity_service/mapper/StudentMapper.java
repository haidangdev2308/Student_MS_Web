package com.webdev.identity_service.mapper;

import com.webdev.identity_service.dto.request.StudentCreationRequest;
import com.webdev.identity_service.dto.response.StudentResponse;
import com.webdev.identity_service.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toStudent(StudentCreationRequest request);
    StudentResponse toStudentResponse(Student student);
}
