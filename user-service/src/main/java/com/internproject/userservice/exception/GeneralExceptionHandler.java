package com.internproject.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<HttpResponse>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), msg), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> generalExceptionHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistsException(EmailExistException e) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<HttpResponse> usernameExistsException(UsernameExistException e) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

}
