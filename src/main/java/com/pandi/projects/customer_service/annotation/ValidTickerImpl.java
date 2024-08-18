package com.pandi.projects.customer_service.annotation;

import com.pandi.projects.customer_service.domain.Ticker;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Objects;

public class ValidTickerImpl implements ConstraintValidator<ValidTicker,Ticker> {


    @Override
    public void initialize(ValidTicker constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Ticker s, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.isNull(s)){
            return false;
        }

       return Arrays.stream(Ticker.values()).anyMatch(e -> e.equals(s));
    }
}
