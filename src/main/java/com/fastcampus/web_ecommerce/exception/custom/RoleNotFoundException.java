package com.fastcampus.web_ecommerce.exception.custom;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super(message);
    }
}
