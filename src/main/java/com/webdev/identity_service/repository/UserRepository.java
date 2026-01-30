package com.webdev.identity_service.repository;

import com.webdev.identity_service.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Tương tác với DBMS
@Repository //anotatio
public interface UserRepository extends JpaRepository<User, String> {
    //repo này quản lý entity User, kiểu dữ liệu Khóa chính là String
    //interface có các hàm sẵn, nơi khác chỉ cần lấy ra dùng, như lưu mới ,sửa, xóa
    boolean existsByUsername(String username); //ton tai username khong
    Optional<User> findByUsername(String username); //jpa tu tim theo username

}
