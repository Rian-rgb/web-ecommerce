package com.fastcampus.web_ecommerce.exception.custom;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
