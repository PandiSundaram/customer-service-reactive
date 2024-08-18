package com.pandi.projects.customer_service.exceptions;

public class CustomerNotFoundException extends RuntimeException{

    private static final String MESSAGE = "Customer Not Found For";

    public CustomerNotFoundException(Integer id){
        super(MESSAGE+id);
    }

}
