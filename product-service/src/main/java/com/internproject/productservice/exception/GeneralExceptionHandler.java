package com.internproject.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus, httpStatus.getReasonPhrase().toUpperCase(), msg), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> generalExceptionHandler(Exception e) {
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<HttpResponse> categoryNotFoundException(CategoryNotFoundException e) {
        return createHttpResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ChangeProductDetailException.class)
    public ResponseEntity<HttpResponse> changeProductDetailException(ChangeProductDetailException e) {
        return createHttpResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<HttpResponse> productNotFoundException(ProductNotFoundException e) {
        return createHttpResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
