package com.cao.shoppingApp.AOP;

import com.cao.shoppingApp.domain.response.ErrorResponse;
import com.cao.shoppingApp.exception.DuplicateUsernameOrEmailException;
import com.cao.shoppingApp.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity handleException(Exception e){
//        return new ResponseEntity(ErrorResponse.builder().message("Different Message").build(), HttpStatus.OK);
//    }

    @ExceptionHandler(value = {DuplicateUsernameOrEmailException.class})
    public ResponseEntity<ErrorResponse> handleDuplicateUsernameOrEmailException(DuplicateUsernameOrEmailException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {InvalidCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

}
