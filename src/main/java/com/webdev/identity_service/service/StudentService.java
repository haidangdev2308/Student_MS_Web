package com.webdev.identity_service.service;

import com.webdev.identity_service.dto.request.StudentCreationRequest;
import com.webdev.identity_service.dto.request.StudentSearchRequest;
import com.webdev.identity_service.dto.response.StudentResponse;
import com.webdev.identity_service.entity.Student;
import com.webdev.identity_service.exception.AppException;
import com.webdev.identity_service.exception.ErrorCode;
import com.webdev.identity_service.mapper.StudentMapper;
import com.webdev.identity_service.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentService {
    StudentRepository studentRepository;
    StudentMapper studentMapper;

    public StudentResponse createStudent(StudentCreationRequest request) {
        if (studentRepository.existsByStudentCode(request.getStudentCode())) {
            throw new AppException(ErrorCode.STUDENT_CODE_EXISTED);
        }
        // Có thể thêm check userId tồn tại trong bảng User nếu cần thiết (gọi UserRepository)

        Student student = studentMapper.toStudent(request);
        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    // Dynamic Search using Specification
    public Page<StudentResponse> searchStudents(StudentSearchRequest criteria, Pageable pageable) {
        Specification<Student> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(criteria.getKeyword())) {
                String likePattern = "%" + criteria.getKeyword().toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("firstName")), likePattern),
                    cb.like(cb.lower(root.get("lastName")), likePattern),
                    cb.like(cb.lower(root.get("studentCode")), likePattern)
                ));
            }

            if (StringUtils.hasText(criteria.getMajor())) {
                predicates.add(cb.equal(root.get("major"), criteria.getMajor()));
            }

            if (criteria.getMinGpa() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("gpa"), criteria.getMinGpa()));
            }
            
            if (criteria.getMaxGpa() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("gpa"), criteria.getMaxGpa()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return studentRepository.findAll(spec, pageable)
                .map(studentMapper::toStudentResponse);
    }
}
