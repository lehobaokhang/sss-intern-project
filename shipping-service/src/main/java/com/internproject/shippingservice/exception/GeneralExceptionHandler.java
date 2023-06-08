package com.internproject.shippingservice.exception;

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

    @ExceptionHandler(ShipNotFoundException.class)
    public ResponseEntity<HttpResponse> emailExistsException(ShipNotFoundException e) {
        return createHttpResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(TrackingException.class)
    public ResponseEntity<HttpResponse> trackingException(TrackingException e) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ShipException.class)
    public ResponseEntity<HttpResponse> shipException(ShipException e) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(RatingException.class)
    public ResponseEntity<HttpResponse> ratingException(RatingException e) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(DistrictNotFoundException.class)
    public ResponseEntity<HttpResponse> districtNotFoundException(DistrictNotFoundException e) {
        return createHttpResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpResponse> notFoundException(NotFoundException e) {
        return createHttpResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
