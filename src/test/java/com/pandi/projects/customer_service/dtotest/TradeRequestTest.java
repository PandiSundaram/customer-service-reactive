package com.pandi.projects.customer_service.dtotest;

import com.pandi.projects.customer_service.domain.Ticker;
import com.pandi.projects.customer_service.domain.TradeType;
import com.pandi.projects.customer_service.dto.TradeRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@Slf4j
public class TradeRequestTest {


   private static Validator validator;

   @BeforeAll
   public static void beforeStart(){
       var factory = Validation.buildDefaultValidatorFactory();
       validator = factory.getValidator();
   }


   @Test
   public void customValidation(){

        var tradeRequest = new TradeRequest(null,null,2, Ticker.AMAZON, TradeType.BUY);
        var setValue = validator.validate(tradeRequest);

       Assertions.assertTrue(setValue.size() == 2 );
   }

    @Test
    public void customValidationEnum(){

        var tradeRequest = new TradeRequest(1,100,2, null, TradeType.BUY);
        var setValue = validator.validate(tradeRequest);

        Assertions.assertTrue(setValue.size() == 1 );
    }


}
