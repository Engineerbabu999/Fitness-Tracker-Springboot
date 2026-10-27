package com.project.fitness.controller;


import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping()
    public ResponseEntity<ActivityResponse> trackActivity(
            @RequestBody ActivityRequest activityRequest,
            @RequestHeader(value = "X-user-id") String userId

    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(activityService.trackActivity(activityRequest, userId));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(
            @RequestHeader(value = "X-user-id") String userId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(activityService.getUserActivities(userId));
    }
}
