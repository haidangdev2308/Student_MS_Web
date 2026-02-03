package com.webdev.identity_service.configuration;

import com.webdev.identity_service.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

//Cấu hình spring security
@Configuration //tự đông bật spring security
@EnableWebSecurity
@EnableMethodSecurity//phân quyen theo method
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS =
            {"/users","/auth/token", "/auth/introspect", "/auth/logout", "/auth/refresh"};

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()//endpoint này co the truy cập mà ko can security jwt
//                        .requestMatchers(HttpMethod.GET, "/users").hasAuthority(Role.ADMIN.name())//endpoint này admin mới gọi được
                        .anyRequest().authenticated());//còn lại phải authen

        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer ->
                        jwtConfigurer.decoder(customJwtDecoder)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))//để cấu hình cho việc đọc jwt của request thì cần jwt decoder
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())//điểm authenticate fail thì đều hướng user đi đâu hoặc trả ra error msg
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);//tat chan csrf
        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {//convert tư scope sang role cho dep
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(""); //spring hiểu scope: "ADMIN"là SCOPE_ADMIN, hàm này sẽ đổi từ SCOPE_ADMIN sang ADMIN
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean //đưa vao bean thi bien nay se duoc dua vao application context de goi o nhung noi khac
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}