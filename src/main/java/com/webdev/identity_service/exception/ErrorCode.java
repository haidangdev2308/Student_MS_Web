package com.webdev.identity_service.exception;


//enum chua cac err code
public enum ErrorCode {
    USER_EXISTED(1002,"User existed"),
    USER_NOT_EXISTED(1005,"User not existed"),
    INVALID_KEY(1001,"Invalid message key"),
    UNCATEGORIZED_EXCEPTION(9999,"UNCATEGORIZED EXCEPTION"),
    USERNAME_INVALID(1003,"Username must be at least 3 characters"),
    INVALID_PASSWORD(1004,"Password must be at least 3 characters"),
    UNAUTHENTICATED(1006,"Unauthenticated"),
    ;

    //khai tao bien ban dau
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
