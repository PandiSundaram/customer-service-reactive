package com.pandi.projects.customer_service.dtotest;

import com.pandi.projects.customer_service.dto.CustomerInfo;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@Slf4j
public class CustomerInfoDtoTest {



    @Test
    public void customerInfo(){

        var customerInfo = new CustomerInfo(1,"pandi",1000, Collections.emptyList());

        Assertions.assertEquals(1,customerInfo.name());

    }

    @Test
    public void customerInfoEqualsHashoad(){

        var customerInfo = new CustomerInfo(1,"pandi",1000, Collections.emptyList());
        var customerInfo1 = new CustomerInfo(1,"pandi",1000, Collections.emptyList());
        var customerInfo2 = new CustomerInfo(1,"pandi1",1000, Collections.emptyList());



        Assertions.assertTrue(customerInfo.equals(customerInfo1));
        Assertions.assertEquals(customerInfo.hashCode(),customerInfo1.hashCode());
        Assertions.assertNotEquals(customerInfo.hashCode(),customerInfo2.hashCode());

    }


}
