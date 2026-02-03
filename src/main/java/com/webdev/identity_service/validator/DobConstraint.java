package com.webdev.identity_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })// apply ở đâu, ở field
@Retention(RUNTIME)//xử lý lúc nào, lúc runtime
@Constraint(validatedBy = { DobValidator.class })//class chịu trách nhiệm validate cho annotation này
public @interface DobConstraint {//@ để báo spring biết là annotation
    String message() default "Invalid date of birth"; //message

    int min();//khai báo gia tri tối thiểu

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
