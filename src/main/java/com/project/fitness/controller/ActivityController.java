package com.project.fitness.controller;


import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping()
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(activityService.trackActivity(activityRequest));
    }

//    @GetMapping
//    public ResponseEntity<List<ActivityResponse>> getActivities(){
//        return "";
//    }
}
