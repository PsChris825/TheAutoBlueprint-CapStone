package learn.autoblueprint.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return makeErrorResponse("Your message was not readable", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return makeErrorResponse("Something terrible happened, sorry :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleException(AuthenticationException e) {
        return makeErrorResponse("Nice try, hacker!", HttpStatus.FORBIDDEN);
    }


    public ResponseEntity<List<String>> makeErrorResponse(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(List.of(message), httpStatus);
    }

}

