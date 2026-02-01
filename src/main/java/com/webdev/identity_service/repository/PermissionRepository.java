package com.webdev.identity_service.repository;

import com.webdev.identity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Tương tác với DBMS
@Repository//tạo bean để tái sd
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
