package com.ecommerce.user.service;


import com.ecommerce.user.dto.UserRegisterDto;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.UserModel;

public class UserMapper {

    // Convert RegisterRequest DTO to UserModel entity
    public static UserModel toEntity(UserRegisterDto request) {
        if (request == null) return null;

        UserModel user = new UserModel();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword()); // encode password in service
        user.setRole(request.getRole());
        return user;
    }

    // Convert UserModel entity to Response DTO
    public static UserResponse toResponse(UserModel user) {
        if (user == null) return null;

        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
        if (user.getRole() != null) {
            response.setRole(user.getRole());
        }
        return response;
    }
}

