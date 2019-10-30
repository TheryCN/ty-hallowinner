package com.github.therycn.tyhallowinner.controller;

import com.github.therycn.tyhallowinner.exception.AthleteNotFoundException;
import com.github.therycn.tyhallowinner.strava.StravaAccessTokenProvider;
import com.github.therycn.tyhallowinner.strava.StravaClient;
import com.github.therycn.tyhallowinner.strava.dto.DetailedActivityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("activities")
public class ActivityController {

    private StravaClient stravaClient;

    private StravaAccessTokenProvider stravaAccessTokenProvider;

    public ActivityController(StravaClient stravaClient, StravaAccessTokenProvider stravaAccessTokenProvider) {
        this.stravaClient = stravaClient;
        this.stravaAccessTokenProvider = stravaAccessTokenProvider;
    }

    @GetMapping
    @RequestMapping("/hello")
    public ResponseEntity<String> helloActivity() {
        return ResponseEntity.ok().body("Hello Activity");
    }

    @GetMapping
    @RequestMapping("/")
    public ResponseEntity<List<DetailedActivityDto>> getAthleteLastTenActivities() {
        try {
            return ResponseEntity.ok().body(stravaClient.getAthleteLastTenActivities(stravaAccessTokenProvider.getAccessToken("thry_charasson")));
        } catch (AthleteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
