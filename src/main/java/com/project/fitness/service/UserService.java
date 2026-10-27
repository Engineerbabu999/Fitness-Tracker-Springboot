package com.project.fitness.service;

import com.project.fitness.dto.RegisterRequest;
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

    public User register(RegisterRequest request){
        User user = new User(
                null,
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                LocalDateTime.parse("2007-12-03T10:15:30"),
                LocalDateTime.parse("2007-12-03T10:15:30"),
                List.of(),
                List.of()
        );

        return userRepository.save(user);
    }
}
