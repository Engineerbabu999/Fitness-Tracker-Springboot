package com.project.fitness.service;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.entities.User;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse register(RegisterRequest request){

        System.out.println("User Full Name ==> "+ request.getFirstName() + request.getLastName());

        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .createdAt(LocalDateTime.parse("2007-12-03T10:15:30"))
                .updatedAt(LocalDateTime.parse("2007-12-03T10:15:30"))
                .build();

        User savedUser = userRepository.save(user);

        return mappedToUserResponse(savedUser);
    }

    private UserResponse mappedToUserResponse(User savedUser) {

        System.out.println("User Full Name ==> "+ savedUser.getFirstName() + savedUser.getLastName());


        UserResponse response = new UserResponse();
        response.setEmail(savedUser.getEmail());
        response.setLastName(savedUser.getLastName());
        response.setFirstName(savedUser.getFirstName());
        response.setId(savedUser.getId());
        response.setPassword(savedUser.getPassword());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());

        return response;
    }
}
