package com.audit_log.infra;

import com.audit_log.exception.AlreadyExistException;
import com.audit_log.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.management.openmbean.KeyAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<String> userNotFoundHandler(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    private  ResponseEntity<String> keyAlreadyExistHandler(AlreadyExistException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<String> runtimeException(RuntimeException runtimeException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(runtimeException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<String> qualquerExcecao(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
