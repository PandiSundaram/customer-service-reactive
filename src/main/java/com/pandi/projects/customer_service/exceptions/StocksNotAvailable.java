package com.pandi.projects.customer_service.exceptions;

public class StocksNotAvailable extends RuntimeException{

    private static final String MESSAGE = "you dont have enough stocks";

    public StocksNotAvailable(){
        super(MESSAGE);
    }

}
