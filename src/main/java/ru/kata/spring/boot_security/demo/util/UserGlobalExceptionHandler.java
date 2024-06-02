package ru.kata.spring.boot_security.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(
            NoSuchUserException exception){
        UserErrorResponse dataException = new UserErrorResponse(
                exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(dataException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(
            IncorrectUserDataException exception){
        UserErrorResponse dataException = new UserErrorResponse(
                exception.getMessage(), LocalDateTime.now()
        );
        return new ResponseEntity<>(dataException, HttpStatus.BAD_GATEWAY);
    }

}
