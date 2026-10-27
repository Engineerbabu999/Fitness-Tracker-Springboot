package com.project.fitness.service;

import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.entities.Activity;
import com.project.fitness.entities.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {

        User user = userRepository.findById(activityRequest.getUserId()).orElseThrow(() -> new RuntimeException("Invalid user: "+ activityRequest.getUserId()));

        Activity activity = Activity.builder()
                .activityType(activityRequest.getActivityType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .startTime(activityRequest.getStartTime())
                .user(user)
                .build();

        Activity savedActivity = activityRepository.save(activity);
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity savedActivity) {
        ActivityResponse activityResponse = new ActivityResponse();

        activityResponse.setId(savedActivity.getId());
        activityResponse.setUserId(savedActivity.getUser().getId());
        activityResponse.setActivityType(savedActivity.getActivityType());
        activityResponse.setDuration(savedActivity.getDuration());
        activityResponse.setCaloriesBurned(savedActivity.getCaloriesBurned());
        activityResponse.setCreatedAt(savedActivity.getCreatedAt());
        activityResponse.setUpdatedAt(savedActivity.getUpdatedAt());
        activityResponse.setStartTime(savedActivity.getStartTime());
        activityResponse.setAdditionalMetrics(savedActivity.getAdditionalMetrics());

        return  activityResponse;
    }
}
