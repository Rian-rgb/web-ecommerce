package com.fastcampus.web_ecommerce.controller;

import com.fastcampus.web_ecommerce.entity.UserInfo;
import com.fastcampus.web_ecommerce.response.user.UserRegisterResponse;
import com.fastcampus.web_ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserRegisterResponse> me() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        UserRegisterResponse userRegisterResponse = UserRegisterResponse.fromUserAndRoles(userInfo.getUser(),
                userInfo.getRoles());
        return ResponseEntity.ok(userRegisterResponse);

    }
}
