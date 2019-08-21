package com.ucx.training.shop.exception;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Data
@Log4j2
public class ResponseException extends Exception {

    private HttpStatus httpStatus;

    public ResponseException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        setHttpStatus(httpStatus);
        log.error(errorMessage);
    }
}
