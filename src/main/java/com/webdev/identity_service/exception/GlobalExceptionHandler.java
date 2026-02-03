package com.webdev.identity_service.exception;

import com.webdev.identity_service.dto.request.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice //gom cac exception ve 1 cho
@Slf4j
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";
    //Truong hop error khong xac dinh duoc
    @ExceptionHandler(value = Exception.class) //bat exception runtime
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse); // tra ve bad request
    }

    // Truong hop error trong pham vi xac dinh duoc
    @ExceptionHandler(value = AppException.class) //bat exception có sẵn error code
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();//lấy được kiểu enum error từ AppException
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())//tra ve http code
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)//lỗi accessdenied
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class) //bat valid exception
    ResponseEntity<ApiResponse> handlingValidException(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
            Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey); //neu key dung

            var constraintViolation = exception.getBindingResult()
                    .getAllErrors().getFirst().unwrap(ConstraintViolation.class);//unwrap toàn bộ thông tin chứa trong exception

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();// lấy param truyền từ annotation

            log.info(attributes.toString());

        } catch (IllegalArgumentException e) { // key sai thi la INVALID_KEY
        }
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(Objects.nonNull(attributes) ?
                mapAttribute(errorCode.getMessage(), attributes)
                : errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse); // tra ve bad request
    }

    //hàm replace param min vào error message
    private String mapAttribute(String message, Map<String, Object> attributes){
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
