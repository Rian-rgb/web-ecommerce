package com.fastcampus.web_ecommerce.response.auth;

import com.fastcampus.web_ecommerce.entity.Role;
import com.fastcampus.web_ecommerce.entity.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthResponse {

    private String token;
    private Long userId;
    private String username;
    private String email;
    private List<String> roles;

    public static AuthResponse fromUserInfo(UserInfo userInfo, String token) {
        return AuthResponse.builder()
                .token(token)
                .userId(userInfo.getUser().getUserId())
                .username(userInfo.getUsername())
                .email(userInfo.getUser().getEmail())
                .roles(userInfo.getRoles().stream().map(Role::getName).toList())
                .build();
    }
}
