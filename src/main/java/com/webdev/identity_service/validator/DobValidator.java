package com.webdev.identity_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {//constrant vilid và kiểu dữ liệu validate

    private int min;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {//kiếm tra
        if (Objects.isNull(value))
            return true;

        long years = ChronoUnit.YEARS.between(value, LocalDate.now());//tinh toán từ value đến hiên tại là bn năm

        return years >= min;
    }

    @Override
    public void initialize(DobConstraint constraintAnnotation) {//chạy trước
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();//lấy được thông số ngta nhập vào
    }
}
