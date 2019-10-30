package com.github.therycn.tyhallowinner.controller;

import com.github.therycn.tyhallowinner.exception.AthleteNotFoundException;
import com.github.therycn.tyhallowinner.strava.StravaAccessTokenProvider;
import com.github.therycn.tyhallowinner.strava.StravaClient;
import com.github.therycn.tyhallowinner.strava.dto.DetailedActivityDto;
import com.github.therycn.tyhallowinner.strava.dto.UpdatableActivityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pumpkin")
public class PumpkinController {

    private StravaClient stravaClient;

    private StravaAccessTokenProvider stravaAccessTokenProvider;

    public PumpkinController(StravaClient stravaClient, StravaAccessTokenProvider stravaAccessTokenProvider) {
        this.stravaClient = stravaClient;
        this.stravaAccessTokenProvider = stravaAccessTokenProvider;
    }

    @GetMapping
    @RequestMapping("/{activityId}")
    public ResponseEntity<DetailedActivityDto> addPumpkin(@PathVariable long activityId) {
        try {
            String accessToken = stravaAccessTokenProvider.getAccessToken("thry_charasson");
            // Retrieve activity name
            DetailedActivityDto detailedActivityDto = stravaClient.getActivityById(accessToken, activityId);

            // Add pumpkin icon to the title
            UpdatableActivityDto updatableActivityDto = new UpdatableActivityDto(detailedActivityDto.getName() + "\uD83C\uDF83");

            // Update activity
            DetailedActivityDto updatedDetailedActivityDto = stravaClient.updateActivityById(accessToken, activityId, updatableActivityDto);

            return ResponseEntity.ok().body(updatedDetailedActivityDto);
        } catch (AthleteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
