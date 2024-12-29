package com.fastcampus.web_ecommerce.util;

import com.fastcampus.web_ecommerce.entity.UserInfo;
import com.fastcampus.web_ecommerce.exception.custom.ForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ValidateRoleUtil {

    public static void validateRole(String roleUser) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        boolean hasAdminRole = userInfo.getRoles().stream()
                .anyMatch(role -> roleUser.equalsIgnoreCase(role.getName()));
        if (hasAdminRole) {
            throw new ForbiddenException("User not allowed");
        }
    }
}
