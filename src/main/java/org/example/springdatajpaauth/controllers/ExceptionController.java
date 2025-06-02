package org.example.springdatajpaauth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity authExcept(){
        return new ResponseEntity<>("Wrong username or password", HttpStatus.UNAUTHORIZED);
    }
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity except(){
//        return new ResponseEntity("Something was wrong.",HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
