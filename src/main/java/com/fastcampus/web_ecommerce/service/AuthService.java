package com.fastcampus.web_ecommerce.service;

import com.fastcampus.web_ecommerce.entity.UserInfo;
import com.fastcampus.web_ecommerce.request.auth.AuthRequest;

public interface AuthService {

    UserInfo authenticate(AuthRequest authRequest);
}
