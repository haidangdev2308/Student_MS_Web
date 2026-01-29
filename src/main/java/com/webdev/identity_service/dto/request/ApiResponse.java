package com.webdev.identity_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

//chuan hoa api res
@JsonInclude(JsonInclude.Include.NON_NULL) //cai nao null thi khong kem vao json
public class ApiResponse <T> {
    private int code = 1000; //1000 mac dinh thanh cong
    private String message;
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
