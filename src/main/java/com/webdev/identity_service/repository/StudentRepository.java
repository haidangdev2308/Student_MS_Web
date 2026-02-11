package com.webdev.identity_service.repository;

import com.webdev.identity_service.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {
    boolean existsByStudentCode(String studentCode);
    boolean existsByUserId(String userId);
}
