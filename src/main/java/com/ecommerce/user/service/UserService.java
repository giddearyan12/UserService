package com.ecommerce.user.service;
import com.ecommerce.user.dto.UserRegisterDto;
import com.ecommerce.user.dto.UserResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse registerUser(UserRegisterDto user);
    UserResponse loginUser(String email, String password);
    Optional<UserResponse> getUserByEmail(String email);
    List<UserResponse> getAllUsers();
}

