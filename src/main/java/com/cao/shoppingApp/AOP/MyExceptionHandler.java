package com.cao.shoppingApp.AOP;

import com.cao.shoppingApp.domain.response.ErrorResponse;
import com.cao.shoppingApp.exception.ConstraintViolationException;
import com.cao.shoppingApp.exception.EmailExistedException;
import com.cao.shoppingApp.exception.UsernameExistedException;
import com.cao.shoppingApp.exception.ZeroOrManyOrdersException;
import io.jsonwebtoken.SignatureException;
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

    @ExceptionHandler(value = {EmailExistedException.class})
    public ResponseEntity<ErrorResponse> handleEmailExistedException(EmailExistedException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {UsernameExistedException.class})
    public ResponseEntity<ErrorResponse> handleUsernameExistedException(UsernameExistedException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {SignatureException.class})
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {ZeroOrManyOrdersException.class})
    public ResponseEntity<ErrorResponse> handleZeroOrManyOrdersException(ZeroOrManyOrdersException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }
}
