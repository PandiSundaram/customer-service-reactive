package com.pandi.projects.customer_service.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomExceptionTest {



    @Test
    public void CustomerNotFoundException(){

        CustomerNotFoundException customerNotFoundException = new CustomerNotFoundException(1);

        Assertions.assertEquals("Customer Not Found For"+1,customerNotFoundException.getMessage());
    }

}
