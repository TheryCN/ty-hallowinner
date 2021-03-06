package com.github.therycn.tyhallowinner.controller;

import com.github.therycn.tyhallowinner.dto.PumpkinActivityStatsDto;
import com.github.therycn.tyhallowinner.entity.PumpkinStats;
import com.github.therycn.tyhallowinner.exception.ActivityNotFoundException;
import com.github.therycn.tyhallowinner.exception.AthleteNotFoundException;
import com.github.therycn.tyhallowinner.service.PumpkinService;
import com.github.therycn.tyhallowinner.strava.StravaAccessTokenProvider;
import com.github.therycn.tyhallowinner.strava.StravaClient;
import com.github.therycn.tyhallowinner.strava.dto.DetailedActivityDto;
import com.github.therycn.tyhallowinner.strava.dto.UpdatableActivityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pumpkin")
@CrossOrigin
public class PumpkinController {

    private StravaClient stravaClient;

    private StravaAccessTokenProvider stravaAccessTokenProvider;

    private PumpkinService pumpkinService;

    public PumpkinController(StravaClient stravaClient, StravaAccessTokenProvider stravaAccessTokenProvider, PumpkinService pumpkinService) {
        this.stravaClient = stravaClient;
        this.stravaAccessTokenProvider = stravaAccessTokenProvider;
        this.pumpkinService = pumpkinService;
    }

    @GetMapping
    @RequestMapping("/{activityId}")
    public ResponseEntity<DetailedActivityDto> addPumpkin(@PathVariable long activityId) {
        try {
            String accessToken = stravaAccessTokenProvider.getAccessToken("thry_charasson");
            // Retrieve activity name
            DetailedActivityDto detailedActivityDto = stravaClient.getActivityById(accessToken, activityId);

            // Add pumpkin icon to the title + description
            PumpkinStats pumpkinStatsByCalories = pumpkinService.getPumpkinStatsByCalories(detailedActivityDto.getCalories());
            UpdatableActivityDto updatableActivityDto = new UpdatableActivityDto(detailedActivityDto.getName() + " \uD83C\uDF83", detailedActivityDto.getDescription() + pumpkinStatsByCalories.beautify());

            // Update activity
            DetailedActivityDto updatedDetailedActivityDto = stravaClient.updateActivityById(accessToken, activityId, updatableActivityDto);

            return ResponseEntity.ok().body(updatedDetailedActivityDto);
        } catch (AthleteNotFoundException | ActivityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @RequestMapping("/eat/{activityId}")
    public ResponseEntity<PumpkinActivityStatsDto> getPumpkinStatsAfterActivity(@PathVariable long activityId) {
        try {
            String accessToken = stravaAccessTokenProvider.getAccessToken("thry_charasson");
            // Retrieve activity
            DetailedActivityDto detailedActivityDto = stravaClient.getActivityById(accessToken, activityId);

            PumpkinStats pumpkinStatsByCalories = pumpkinService.getPumpkinStatsByCalories(detailedActivityDto.getCalories());
            return ResponseEntity.ok().body(new PumpkinActivityStatsDto(detailedActivityDto.getId(), detailedActivityDto.getName(), detailedActivityDto.getDistance(), detailedActivityDto.getMovingTime(), detailedActivityDto.getCalories(), pumpkinStatsByCalories.getPumpkinsYouCanEat(), pumpkinStatsByCalories.getProteins(), pumpkinStatsByCalories.getCarbohydrates()));
        } catch (AthleteNotFoundException | ActivityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
