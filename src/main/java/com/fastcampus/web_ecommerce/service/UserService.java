package com.fastcampus.web_ecommerce.service;

import com.fastcampus.web_ecommerce.request.auth.UserRegisterRequest;
import com.fastcampus.web_ecommerce.request.user.UserUpdateRequest;
import com.fastcampus.web_ecommerce.response.user.GetUserResponse;
import com.fastcampus.web_ecommerce.response.user.UpdateUserResponse;
import com.fastcampus.web_ecommerce.response.user.UserRegisterResponse;

public interface UserService {

    UserRegisterResponse register(UserRegisterRequest request);

    GetUserResponse findById(Long id);

    GetUserResponse findByKeyword(String keyword);

    UpdateUserResponse updateUser(UserUpdateRequest request);

    void deleteUser(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
