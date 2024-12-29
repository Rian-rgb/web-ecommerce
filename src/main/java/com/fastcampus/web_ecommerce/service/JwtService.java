package com.fastcampus.web_ecommerce.service;

import com.fastcampus.web_ecommerce.entity.UserInfo;

public interface JwtService {

    String generateToken(UserInfo userInfo);

    boolean validateToken(String token);

    String getUsernameFromToken(String token);
}
