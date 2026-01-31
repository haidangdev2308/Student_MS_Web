package com.webdev.identity_service.configuration;

import com.webdev.identity_service.entity.User;
import com.webdev.identity_service.enums.Role;
import com.webdev.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

//tạo user admin mỗi khi start application này lên
@Configuration // 1. Báo hiệu Class này là nơi chứa các lệnh tạo Bean
@RequiredArgsConstructor //khai bao bean khong can autowire
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j // inject log
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    //được khởi chạy moi khi application được start
    // 2. Spring nhìn thấy @Bean -> Nó sẽ chạy hàm này NGAY LẬP TỨC khi khởi động
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {//start ma chua co user admin thi moi tao admin
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .username("admin")
                        .roles(roles)
                        .password(passwordEncoder.encode("admin"))
                        .build();

                userRepository.save(user);

                log.warn("user admin has been created with default password: admin, please change it!");
            }
        };
    }
}
