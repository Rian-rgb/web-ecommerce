package com.fastcampus.web_ecommerce.service.impl;

import com.fastcampus.web_ecommerce.entity.Role;
import com.fastcampus.web_ecommerce.entity.User;
import com.fastcampus.web_ecommerce.entity.UserInfo;
import com.fastcampus.web_ecommerce.exception.custom.UserNotFoundException;
import com.fastcampus.web_ecommerce.repository.RoleRepository;
import com.fastcampus.web_ecommerce.repository.UserRepository;
import com.fastcampus.web_ecommerce.service.CacheService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final String USER_CACHE_KEY = "cache:user:";
    private final String USER_ROLES_CACHE_KEY = "cache:user:roles:";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CacheService cacheService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String userCacheKey = USER_CACHE_KEY + username;
        String rolesCacheKey = USER_ROLES_CACHE_KEY + username;

        Optional<User> userOpt = cacheService.get(userCacheKey, User.class);
        Optional<List<Role>> rolesOpt = cacheService.get(userCacheKey, new TypeReference<List<Role>>() {
        });

        if (userOpt.isPresent() && rolesOpt.isPresent()) {
            return UserInfo.builder()
                    .roles(rolesOpt.get())
                    .user(userOpt.get())
                    .build();
        }

        User user = userRepository.findByKeyword(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username " + username));

        List<Role> roles = roleRepository.findByUserId(user.getUserId());

        UserInfo userInfo = UserInfo.builder()
                .user(user)
                .roles(roles)
                .build();

        cacheService.put(userCacheKey, user);
        cacheService.put(rolesCacheKey, roles);
        return userInfo;

    }
}
