package com.pandi.projects.customer_service.annotation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy =  ValidTypImpl.class)
public @interface TradeTypeValid {
}
