package com.besttradebank.investment.customer.dto;

import java.time.Instant;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Error {

    private int status;
    private String error;
    private String message;
    private Instant timestamp;

    public Error() {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        status = internalServerError.value();
        error = internalServerError.getReasonPhrase();
        timestamp = Instant.now();
    }

    public Error(HttpStatus httpStatus, String message) {
        status = httpStatus.value();
        error = httpStatus.getReasonPhrase();
        this.message = message;
        timestamp = Instant.now();
    }
}
