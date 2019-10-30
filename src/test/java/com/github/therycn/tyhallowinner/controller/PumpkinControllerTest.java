package com.github.therycn.tyhallowinner.controller;

import com.github.therycn.tyhallowinner.exception.ActivityNotFoundException;
import com.github.therycn.tyhallowinner.exception.AthleteNotFoundException;
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

    private PumpkinController pumpkinController;

    private StravaClient stravaClient;

    private StravaAccessTokenProvider stravaAccessTokenProvider;

    @BeforeEach
    public void init() {
        stravaClient = Mockito.mock(StravaClient.class);
        stravaAccessTokenProvider = Mockito.mock(StravaAccessTokenProvider.class);
        pumpkinController = new PumpkinController(stravaClient, stravaAccessTokenProvider);
    }

    @Test
    public void whenAddPumpkin_thenReturnDetailedActivityDto() throws AthleteNotFoundException, ActivityNotFoundException {
        // Given
        long activityId = 1l;
        String accessToken = "ACCESS_TOKEN";
        Mockito.when(stravaAccessTokenProvider.getAccessToken(Mockito.anyString())).thenReturn(accessToken);

        DetailedActivityDto detailedActivityDto = new DetailedActivityDto(activityId, "Morning Run", 10000f, 600, null, 100f, 0f);
        Mockito.when(stravaClient.getActivityById(accessToken, activityId)).thenReturn(detailedActivityDto);

        UpdatableActivityDto updatableActivityDto = new UpdatableActivityDto("Morning Run\uD83C\uDF83");
        DetailedActivityDto updatedDetailedActivityDto = new DetailedActivityDto(activityId, updatableActivityDto.getName(), 10000f, 600, null, 100f, 0f);
        Mockito.when(stravaClient.updateActivityById(accessToken, activityId, updatableActivityDto)).thenReturn(updatedDetailedActivityDto);

        // When
        ResponseEntity<DetailedActivityDto> responseEntity = pumpkinController.addPumpkin(activityId);

        // Then
        Mockito.verify(stravaAccessTokenProvider).getAccessToken(Mockito.anyString());
        Mockito.verify(stravaClient).getActivityById(accessToken, activityId);
        Mockito.verify(stravaClient).updateActivityById(accessToken, activityId, updatableActivityDto);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(updatedDetailedActivityDto);
    }

    @Test
    public void whenAddPumpkin_thenReturnNotFound() throws AthleteNotFoundException, ActivityNotFoundException {
        // Given
        long activityId = 1l;
        String accessToken = "ACCESS_TOKEN";
        Mockito.when(stravaAccessTokenProvider.getAccessToken(Mockito.anyString())).thenReturn(accessToken);

        Mockito.when(stravaClient.getActivityById(accessToken, activityId)).thenThrow(new ActivityNotFoundException());

        // When
        ResponseEntity<DetailedActivityDto> responseEntity = pumpkinController.addPumpkin(activityId);

        // Then
        Mockito.verify(stravaAccessTokenProvider).getAccessToken(Mockito.anyString());
        Mockito.verify(stravaClient).getActivityById(accessToken, activityId);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}