package com.pandi.projects.customer_service.advice;


import com.pandi.projects.customer_service.exceptions.CustomerNotFoundException;
import com.pandi.projects.customer_service.exceptions.InSufficientBalanceException;
import com.pandi.projects.customer_service.exceptions.StocksNotAvailable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CustomerControllerAdvice {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail customerNotFoundHandler(CustomerNotFoundException ex){

        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
        problemDetails.setTitle("CUSTOMER_NOT_FOUND");

        return problemDetails;
    }

    @ExceptionHandler(InSufficientBalanceException.class)
    public ProblemDetail insufficientFundHandler(InSufficientBalanceException ex){

        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
        problemDetails.setTitle("INSUFFICIENT_FUND");

        return problemDetails;
    }

    @ExceptionHandler(StocksNotAvailable.class)
    public ProblemDetail stockNotAvailableHandler(StocksNotAvailable ex){

        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
        problemDetails.setTitle("NOT_ENOUGH_FUND");

        return problemDetails;
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ProblemDetail inputValidationHandler(WebExchangeBindException exception){

        var messages = exception.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,messages.stream().collect(Collectors.joining(",")));
        problemDetail.setTitle("INVALID_INPUT_PARAM");

        return problemDetail;


    }




}
