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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class PumpkinControllerTest {

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private PumpkinController pumpkinController;

    private StravaClient stravaClient;

    private StravaAccessTokenProvider stravaAccessTokenProvider;

    private PumpkinService pumpkinService;

    @BeforeEach
    public void init() {
        stravaClient = Mockito.mock(StravaClient.class);
        stravaAccessTokenProvider = Mockito.mock(StravaAccessTokenProvider.class);
        pumpkinService = Mockito.mock(PumpkinService.class);
        pumpkinController = new PumpkinController(stravaClient, stravaAccessTokenProvider, pumpkinService);
    }

    @Test
    public void whenAddPumpkin_thenReturnDetailedActivityDto() throws AthleteNotFoundException, ActivityNotFoundException {
        // Given
        long activityId = 1l;
        Mockito.when(stravaAccessTokenProvider.getAccessToken(Mockito.anyString())).thenReturn(ACCESS_TOKEN);

        DetailedActivityDto detailedActivityDto = new DetailedActivityDto(activityId, "Morning Run", "", 10000f, 600, null, 100f, 0f, 0f);
        Mockito.when(stravaClient.getActivityById(ACCESS_TOKEN, activityId)).thenReturn(detailedActivityDto);

        PumpkinStats pumpkinStats = new PumpkinStats(2.5407693f, 127.03847f, 825.75006f);
        Mockito.when(pumpkinService.getPumpkinStatsByCalories(detailedActivityDto.getCalories())).thenReturn(pumpkinStats);

        UpdatableActivityDto updatableActivityDto = new UpdatableActivityDto("Morning Run\uD83C\uDF83", pumpkinStats.toString());
        DetailedActivityDto updatedDetailedActivityDto = new DetailedActivityDto(activityId, updatableActivityDto.getName(), "", 10000f, 600, null, 100f, 0f, 0f);
        Mockito.when(stravaClient.updateActivityById(ACCESS_TOKEN, activityId, updatableActivityDto)).thenReturn(updatedDetailedActivityDto);

        // When
        ResponseEntity<DetailedActivityDto> responseEntity = pumpkinController.addPumpkin(activityId);

        // Then
        Mockito.verify(stravaAccessTokenProvider).getAccessToken(Mockito.anyString());
        Mockito.verify(stravaClient).getActivityById(ACCESS_TOKEN, activityId);
        Mockito.verify(stravaClient).updateActivityById(ACCESS_TOKEN, activityId, updatableActivityDto);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(updatedDetailedActivityDto);
    }

    @Test
    public void whenAddPumpkin_thenReturnNotFound() throws AthleteNotFoundException, ActivityNotFoundException {
        // Given
        long activityId = 1l;
        Mockito.when(stravaAccessTokenProvider.getAccessToken(Mockito.anyString())).thenReturn(ACCESS_TOKEN);

        Mockito.when(stravaClient.getActivityById(ACCESS_TOKEN, activityId)).thenThrow(new ActivityNotFoundException());

        // When
        ResponseEntity<DetailedActivityDto> responseEntity = pumpkinController.addPumpkin(activityId);

        // Then
        Mockito.verify(stravaAccessTokenProvider).getAccessToken(Mockito.anyString());
        Mockito.verify(stravaClient).getActivityById(ACCESS_TOKEN, activityId);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void whenGetPumpkinStatsAfterActivity_thenReturnPumpkinActivityStatsDto() throws AthleteNotFoundException, ActivityNotFoundException {
        // Given
        long activityId = 1l;
        Mockito.when(stravaAccessTokenProvider.getAccessToken(Mockito.anyString())).thenReturn(ACCESS_TOKEN);

        DetailedActivityDto detailedActivityDto = new DetailedActivityDto(activityId, "Morning Run", "", 10000f, 600, null, 100f, 0f, 3303.0f);
        Mockito.when(stravaClient.getActivityById(ACCESS_TOKEN, activityId)).thenReturn(detailedActivityDto);

        PumpkinStats pumpkinStats = new PumpkinStats(2.5407693f, 127.03847f, 825.75006f);
        Mockito.when(pumpkinService.getPumpkinStatsByCalories(detailedActivityDto.getCalories())).thenReturn(pumpkinStats);

        PumpkinActivityStatsDto expectedPumpkinActivityStatsDto = new PumpkinActivityStatsDto(detailedActivityDto.getId(), detailedActivityDto.getName(), detailedActivityDto.getDistance(), detailedActivityDto.getMovingTime(), detailedActivityDto.getCalories(), pumpkinStats.getPumpkinsYouCanEat(), pumpkinStats.getProteins(), pumpkinStats.getCarbohydrates());

        // When
        ResponseEntity<PumpkinActivityStatsDto> responseEntity = pumpkinController.getPumpkinStatsAfterActivity(activityId);

        // Then
        Mockito.verify(stravaAccessTokenProvider).getAccessToken(Mockito.anyString());
        Mockito.verify(stravaClient).getActivityById(ACCESS_TOKEN, activityId);
        Mockito.verify(pumpkinService).getPumpkinStatsByCalories(detailedActivityDto.getCalories());

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(expectedPumpkinActivityStatsDto);
    }
}