package com.pandi.projects.customer_service.exceptions;

public class InSufficientBalanceException extends RuntimeException{

    private static final String MESSAGE = "your account has insufficient funds";

    public InSufficientBalanceException(Integer customerId){
        super(MESSAGE+customerId);
    }

}
