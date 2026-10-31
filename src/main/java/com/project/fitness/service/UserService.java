package com.project.fitness.service;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.entities.User;

import com.project.fitness.entities.UserRole;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request){

        System.out.println("User Full Name ==> "+ request.getFirstName() + request.getLastName());

        UserRole role = request.getRole() != null ? request.getRole() : UserRole.USER;
        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(role)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        return mappedToUserResponse(savedUser);
    }

    public UserResponse mappedToUserResponse(User savedUser) {

        System.out.println("User Full Name ==> "+ savedUser.getFirstName() + savedUser.getLastName());


        UserResponse response = new UserResponse();
        response.setEmail(savedUser.getEmail());
        response.setLastName(savedUser.getLastName());
        response.setFirstName(savedUser.getFirstName());
        response.setId(savedUser.getId());
        response.setPassword(savedUser.getPassword());
        response.setRole(savedUser.getRole());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());

        return response;
    }
}
