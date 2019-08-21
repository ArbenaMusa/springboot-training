package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.ErrorDTO;
import com.ucx.training.shop.exception.ResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ErrorDTO> handleApiException(ResponseException responseException, WebRequest webRequest) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .timeStamp(LocalDateTime.now())
                .errorMessage(responseException.getMessage())
                .path(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDTO, responseException.getHttpStatus());
    }
}
