package com.fastcampus.web_ecommerce.exception.custom;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
