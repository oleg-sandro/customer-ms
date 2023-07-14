package com.besttradebank.investment.customer.exception.handler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.besttradebank.investment.customer.dto.Error;
import com.besttradebank.investment.customer.exception.CustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<Error> handleCustomerException(CustomerException e) {
        String message = e.getMessage();
        log.error(message);
        return ResponseEntity.internalServerError().body(new Error(INTERNAL_SERVER_ERROR, message));
    }
}
