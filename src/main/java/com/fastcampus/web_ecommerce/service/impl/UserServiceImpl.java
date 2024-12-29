package com.fastcampus.web_ecommerce.service.impl;

import com.fastcampus.web_ecommerce.entity.Role;
import com.fastcampus.web_ecommerce.entity.User;
import com.fastcampus.web_ecommerce.entity.UserRole;
import com.fastcampus.web_ecommerce.exception.custom.*;
import com.fastcampus.web_ecommerce.repository.RoleRepository;
import com.fastcampus.web_ecommerce.repository.UserRepository;
import com.fastcampus.web_ecommerce.repository.UserRoleRepository;
import com.fastcampus.web_ecommerce.request.auth.UserRegisterRequest;
import com.fastcampus.web_ecommerce.request.user.UserUpdateRequest;
import com.fastcampus.web_ecommerce.response.user.GetUserResponse;
import com.fastcampus.web_ecommerce.response.user.UpdateUserResponse;
import com.fastcampus.web_ecommerce.response.user.UserRegisterResponse;
import com.fastcampus.web_ecommerce.service.CacheService;
import com.fastcampus.web_ecommerce.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final String USER_CACHE_KEY = "cache:user:";
    private final String USER_ROLES_CACHE_KEY = "cache:user:roles:";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CacheService cacheService;

    @Transactional
    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {

        if (existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already taken");
        }
        if (existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already taken");
        }
        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            throw new BadRequestException("Password confirmation doesn't match");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .enabled(true)
                .password(encodedPassword)
                .build();
        user = userRepository.save(user);

        Role userRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RoleNotFoundException("Default role not found"));

        UserRole userRoleRelation = UserRole.builder()
                .id(new UserRole.UserRoleId(user.getUserId(), userRole.getRoleId()))
                .build();
        userRoleRepository.save(userRoleRelation);

        return UserRegisterResponse.fromUserAndRoles(user, List.of(userRole));

    }

    @Override
    public GetUserResponse findById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
        List<Role> roles = roleRepository.findByUserId(id);
        return GetUserResponse.fromUserAndRoles(user, roles);

    }

    @Override
    public GetUserResponse findByKeyword(String keyword) {

        User user = userRepository.findByKeyword(keyword)
                .orElseThrow(() -> new UserNotFoundException("User not found with username / email " + keyword));
        List<Role> roles = roleRepository.findByUserId(user.getUserId());
        return GetUserResponse.fromUserAndRoles(user, roles);

    }

    @Override
    public UpdateUserResponse updateUser(UserUpdateRequest request) {

        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + request.getId()));

        // Jika user mengubah password
        if (request.getCurrentPassword() != null && request.getNewPassword() != null) {
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new InvalidPasswordException("Current password is incorrect");
            }

            String encodedPassword = passwordEncoder.encode(request.getNewPassword());
            user.setPassword(encodedPassword);
        }

        // Jika user mengubah username
        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            if (existsByUsername(request.getUsername())) {
                throw new UsernameAlreadyExistsException(
                        "Username %s is already taken".formatted(request.getUsername()));
            }

            user.setUsername(request.getUsername());
        }

        // Jika user mengubah email
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException(
                        "Email %s is already taken".formatted(request.getUsername()));
            }

            user.setEmail(request.getEmail());
        }

        String userCacheKey = USER_CACHE_KEY + user.getUsername();
        String roleCacheKey = USER_ROLES_CACHE_KEY + user.getUsername();

        userRepository.save(user);

        List<Role> roles = roleRepository.findByUserId(user.getUserId());

        cacheService.evict(userCacheKey);
        cacheService.evict(roleCacheKey);
        return UpdateUserResponse.fromUserAndRoles(user, roles);

    }

    @Transactional
    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
        userRoleRepository.deleteByIdUserId(id);
        userRepository.delete(user);

    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByusername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
