package com.internproject.shippingservice.exception;

public class DistrictNotFoundException extends RuntimeException{
    public DistrictNotFoundException(String message) {
        super(message);
    }
}
