package com.fastcampus.web_ecommerce.exception.custom;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message) {
        super(message);
    }
}
