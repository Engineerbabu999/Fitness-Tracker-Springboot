package com.project.fitness.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @PostMapping()
    public ResponseEntity<ActivityResponse> trackActivity(){
        return "";
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getActivities(){
        return "";
    }
}
