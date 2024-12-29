package com.fastcampus.web_ecommerce.controller;

import com.fastcampus.web_ecommerce.entity.UserInfo;
import com.fastcampus.web_ecommerce.request.auth.AuthRequest;
import com.fastcampus.web_ecommerce.request.auth.UserRegisterRequest;
import com.fastcampus.web_ecommerce.response.auth.AuthResponse;
import com.fastcampus.web_ecommerce.response.user.UserRegisterResponse;
import com.fastcampus.web_ecommerce.service.AuthService;
import com.fastcampus.web_ecommerce.service.JwtService;
import com.fastcampus.web_ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
            ) {

        UserInfo userInfo = authService.authenticate(request);
        String token = jwtService.generateToken(userInfo);
        AuthResponse authResponse = AuthResponse.fromUserInfo(userInfo, token);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(
            @RequestBody @Valid UserRegisterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }
}
