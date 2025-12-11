package com.ecommerce.user.service;
import com.ecommerce.user.model.UserModel;
import java.util.List;
import java.util.Optional;

public interface UserService {
    UserModel registerUser(UserModel user);
    Optional<UserModel> getUserByEmail(String email);
    List<UserModel> getAllUsers();
}

