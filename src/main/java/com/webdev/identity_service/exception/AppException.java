package com.webdev.identity_service.exception;

//AppException để lấy được enum errorCode
public class AppException extends RuntimeException {
    // 1. Kế thừa RuntimeException để Java hiểu đây là một cái Lỗi (Unchecked Exception),
    // giúp chương trình dừng lại ngay lập tức khi được ném ra.
    public AppException(ErrorCode errorCode) {
        // 2. Gọi lên thằng cha (RuntimeException) để nó in cái message ra log (console) cho Developer xem.
        super(errorCode.getMessage());
        // 3. Quan trọng nhất: Lưu cái cục ErrorCode vào biến riêng để tí nữa thằng Handler lấy ra dùng.
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;

    // Getter để GlobalExceptionHandler lấy thông tin ra.
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
