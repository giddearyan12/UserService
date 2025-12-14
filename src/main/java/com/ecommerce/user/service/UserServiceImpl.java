package com.ecommerce.user.service;

import com.ecommerce.user.dto.UserRegisterDto;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.UserModel;
import com.ecommerce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserResponse registerUser(UserRegisterDto userDto) {
        Optional<UserModel> existingUserByEmail = userRepository.findByEmail(userDto.getEmail());
        if (existingUserByEmail.isPresent()) {
            throw new com.ecommerce.user.exception.DuplicateEmailException("Email is already registered");
        }

        Optional<UserModel> existingUserByPhone = userRepository.findByPhone(userDto.getPhone());
        if (existingUserByPhone.isPresent()) {
            throw new com.ecommerce.user.exception.DuplicatePhoneException("Phone number is already registered");
        }

        // Convert DTO to entity
        UserModel user = UserMapper.toEntity(userDto);
        // set default role if not provided
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("user");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // encode password

        UserModel savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser); // return response DTO
    }

    @Override
    public UserResponse loginUser(String email, String password) {
        UserModel existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new com.ecommerce.user.exception.InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new com.ecommerce.user.exception.InvalidCredentialsException("Invalid email or password");
        }

        return UserMapper.toResponse(existingUser);
    }

    @Override
    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toResponse);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }
}
