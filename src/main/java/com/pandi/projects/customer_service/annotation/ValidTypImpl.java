package com.pandi.projects.customer_service.annotation;

import com.pandi.projects.customer_service.domain.TradeType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Objects;

public class ValidTypImpl implements ConstraintValidator<TradeTypeValid, TradeType> {
    @Override
    public void initialize(TradeTypeValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TradeType tradeType, ConstraintValidatorContext constraintValidatorContext) {
         if(Objects.isNull(tradeType)){
             return false;
         }

        return Arrays.stream(TradeType.values()).anyMatch(e -> e.equals(tradeType));
    }
}
