package com.example.hexagonal.infrastructure.exceptions;

import com.example.hexagonal.application.exceptions.PriceNotFoundException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class hexagonalControllerAdvice {


    @ExceptionHandler(PriceNotFoundException.class)
    private static ResponseEntity<Object> handlePriceNotFoundException(PriceNotFoundException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>("Error: " +  exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralExceptions(Exception ex) {
        String errorMessage = "Error: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
