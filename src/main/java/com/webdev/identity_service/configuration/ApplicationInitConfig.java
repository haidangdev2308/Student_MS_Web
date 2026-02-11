package com.webdev.identity_service.configuration;

import com.webdev.identity_service.entity.User;
import com.webdev.identity_service.enums.Role;
import com.webdev.identity_service.repository.RoleRepository;
import com.webdev.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            // 1. Đảm bảo Role ADMIN tồn tại
            var adminRole = roleRepository.findById(Role.ADMIN.name()).orElseGet(() -> {
                var newRole = com.webdev.identity_service.entity.Role.builder()
                        .name(Role.ADMIN.name())
                        .description("Admin role")
                        .build();
                return roleRepository.save(newRole);
            });

            // 2. Tạo User admin nếu chưa có
            if (userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<com.webdev.identity_service.entity.Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("User 'admin' has been created with default password: 'admin'. Please change it!");
            }
        };
    }
}
