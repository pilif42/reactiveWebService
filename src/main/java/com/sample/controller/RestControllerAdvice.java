package com.sample.controller;

import com.sample.dto.ErrorDto;
import com.sample.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

// Targets all Controllers annotated with @RestController
@ControllerAdvice(annotations = RestController.class)
public class RestControllerAdvice {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorDto> customerNotFoundExceptionHandler(CustomerNotFoundException e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
