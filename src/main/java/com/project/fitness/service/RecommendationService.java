package com.project.fitness.service;

import com.project.fitness.dto.RecommendationRequest;
import com.project.fitness.entities.Activity;
import com.project.fitness.entities.Recommendation;
import com.project.fitness.entities.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.RecommendationRepository;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {


    private final ActivityRepository activityRepository;
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;


    public Recommendation generateRecommendation(RecommendationRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(()-> new RuntimeException("User with this ID not found :-> "+request.getUserId()));

        Activity activity = activityRepository.findById(request.getActivityId()).orElseThrow(()-> new RuntimeException("Activity with this ID not found :-> "+request.getActivityId()));

        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .activity(activity)
                .improvements(request.getImprovements())
                .suggestions(request.getSuggestions())
                .safety(request.getSafety())
                .type(request.getType())
                .build();

        return recommendationRepository.save(recommendation);
    }

    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }
}
