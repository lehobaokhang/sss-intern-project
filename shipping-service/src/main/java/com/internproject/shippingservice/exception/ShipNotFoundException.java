package com.internproject.shippingservice.exception;

public class ShipNotFoundException extends RuntimeException{
    public ShipNotFoundException(String message) {
        super(String.format("Can not find any shipping information from id: %s", message));
    }
}
