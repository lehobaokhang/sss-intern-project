package com.internproject.shippingservice.exception;

public class ShipNotFoundException extends RuntimeException{
    public ShipNotFoundException(String message) {
        super(message);
    }
}
