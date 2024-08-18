package com.pandi.projects.customer_service.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidTickerImpl.class)
//Target
//Retention
//constant
public @interface ValidTicker {

    String message() default "invalid enum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
