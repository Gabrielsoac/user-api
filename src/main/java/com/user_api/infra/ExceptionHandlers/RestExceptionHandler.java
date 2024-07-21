package com.user_api.infra.ExceptionHandlers;

import com.user_api.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<String> userNotFoundExceptionHandler(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    private ResponseEntity<String> userAlreadyExistsExceptionHandler(UsernameAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(UserListIsEmptyException.class)
    private ResponseEntity<String> userListIsEmptyExceptionHandler(UserListIsEmptyException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(AddressNotFoundException.class)
    private ResponseEntity<String> addressNotFoundExceptionHandler(AddressNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    private ResponseEntity<String> userEmailAlreadyExistsExceptionHandler(UserEmailAlreadyExistsException e){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
