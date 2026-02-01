package com.webdev.identity_service.repository;

import com.webdev.identity_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//tạo bean để tái sd
public interface RoleRepository extends JpaRepository<Role,String> {

}
