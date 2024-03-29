package com.internproject.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
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
        e.printStackTrace();
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistsException(EmailExistException e) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<HttpResponse> usernameExistsException(UsernameExistException e) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<HttpResponse> roleNotFoundException(RoleNotFoundException e) {
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> signatureException(AccessDeniedException e) {
        return createHttpResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException(BadCredentialsException e) {
        return createHttpResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException e) {
        return createHttpResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DoOnOtherUserInformationException.class)
    public ResponseEntity<HttpResponse> changeOtherUserInformationException(DoOnOtherUserInformationException e) {
        return createHttpResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(ChangePasswordException.class)
    public ResponseEntity<HttpResponse> changePasswordException(ChangePasswordException e) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AddressException.class)
    public ResponseEntity<HttpResponse> addressException(AddressException e) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<HttpResponse> roleException(RoleException e) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
