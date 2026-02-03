package com.webdev.identity_service.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.webdev.identity_service.dto.request.AuthenticationRequest;
import com.webdev.identity_service.dto.request.IntrospectRequest;
import com.webdev.identity_service.dto.request.LogoutRequest;
import com.webdev.identity_service.dto.request.RefreshRequest;
import com.webdev.identity_service.dto.response.AuthenticationResponse;
import com.webdev.identity_service.dto.response.IntrospectResponse;
import com.webdev.identity_service.entity.InvalidatedToken;
import com.webdev.identity_service.entity.User;
import com.webdev.identity_service.exception.AppException;
import com.webdev.identity_service.exception.ErrorCode;
import com.webdev.identity_service.repository.InvalidatedTokenRepository;
import com.webdev.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service //layer phụ trách xử lý logic
@RequiredArgsConstructor //tự dộng bing với dependency vào contructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}") // đọc từ file config yaml
    protected String SIGNER_KEY;

    //verify jwt
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);//parse token tu request

        Date expireTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(jwsVerifier);//dung chu ky jwt de verify, trả về bool kiem tra verify

        try {
            verifyToken(token);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) { // method xac thuc
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10); //ma hoa bcript
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));// tim user xem co khop voi username vua nhap khong

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());//so pw tu request voi pw cua user vua tim duoc
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public void logout(LogoutRequest request)
            throws ParseException, JOSEException {//request la 1 token
        var signToken = verifyToken(request.getToken());

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

    public AuthenticationResponse refreshToken(RefreshRequest request)
            throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken());

        //verify ok thì cấp token mới
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        //invalidate token cũ
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();//lấy username

        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.UNAUTHENTICATED)
        );

        var token = generateToken(user);//tạo token mới cho user đó

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }


    private String generateToken(User user) { // tao JWT
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);//header

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()//build payload
                .subject(user.getUsername())
                .issuer("studentMSWeb.com")
                .issueTime(new Date())//luc tao
                .expirationTime(
                        new Date(
                                Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli() //exp 1 gio
                        )
                )
                .jwtID(UUID.randomUUID().toString())//tạo id cho jwt
                .claim("scope",buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject()); //payload nhan json obj

        JWSObject jwsObject = new JWSObject(jwsHeader,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes())); //thuat toan ky: khoa ky vaf khoa giai ma trung nhau
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can not create token");
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);//parse token tu request

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);//dung chu ky jwt de verify, trả về bool kiem tra verify

        if (!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID()))//nếu token đã invalid thì ra lỗi
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    private String buildScope(User user) {// tao claim scope khi dang nhap
        StringJoiner stringJoiner = new StringJoiner(" ");
//        if(!CollectionUtils.isEmpty(user.getRoles())) { // role khong empty thi thuc hien
//            user.getRoles().forEach(stringJoiner::add);
//        }

        if (!CollectionUtils.isEmpty(user.getRoles()))// role khong empty thi thuc hien
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName()); // thành ROLE_ADMIN
                if (!CollectionUtils.isEmpty(role.getPermissions()))//role có permission thi thuc hien
                    role.getPermissions() // add permission vao scope
                            .forEach(permission -> stringJoiner.add(permission.getName()));
            });
        return stringJoiner.toString();
    }
}
