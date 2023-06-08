package com.internproject.productservice.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(String.format("Can not find any product with id: %s", message));
    }
}
