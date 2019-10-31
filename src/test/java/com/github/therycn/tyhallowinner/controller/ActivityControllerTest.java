package com.github.therycn.tyhallowinner.controller;

import com.github.therycn.tyhallowinner.exception.AthleteNotFoundException;
import com.github.therycn.tyhallowinner.strava.StravaAccessTokenProvider;
import com.github.therycn.tyhallowinner.strava.StravaClient;
import com.github.therycn.tyhallowinner.strava.dto.DetailedActivityDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

class ActivityControllerTest {

    private ActivityController activityController;

    private StravaClient stravaClient;

    private StravaAccessTokenProvider stravaAccessTokenProvider;

    @BeforeEach
    public void init() {
        stravaClient = Mockito.mock(StravaClient.class);
        stravaAccessTokenProvider = Mockito.mock(StravaAccessTokenProvider.class);
        activityController = new ActivityController(stravaClient, stravaAccessTokenProvider);
    }

    @Test
    public void whenHelloActivity_thenReturnHello() {
        // Given
        // When
        ResponseEntity<String> activityResponseEntity = activityController.helloActivity();
        // Then
        Assertions.assertThat(activityResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(activityResponseEntity.getBody()).isEqualTo("Hello Activity");
    }

    @Test
    public void whenGetAthleteLastTenActivities_thenReturnDetailedActivityDto() throws AthleteNotFoundException {
        // Given
        String accessToken = "ACCESS_TOKEN";
        Mockito.when(stravaAccessTokenProvider.getAccessToken(Mockito.anyString())).thenReturn(accessToken);

        DetailedActivityDto detailedActivityDto = new DetailedActivityDto(1l, "Running", "", 10000f, 600, null, 100f, 0f, 0f);
        Mockito.when(stravaClient.getAthleteLastTenActivities(accessToken)).thenReturn(Arrays.asList(detailedActivityDto));

        // When
        ResponseEntity<List<DetailedActivityDto>> responseEntity = activityController.getAthleteLastTenActivities();

        // Then
        Mockito.verify(stravaAccessTokenProvider).getAccessToken(Mockito.anyString());
        Mockito.verify(stravaClient).getAthleteLastTenActivities(accessToken);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).containsExactly(detailedActivityDto);
    }

    @Test
    public void whenGetAthleteLastTenActivities_thenReturnNotFound() throws AthleteNotFoundException {
        // Given
        Mockito.when(stravaAccessTokenProvider.getAccessToken(Mockito.anyString())).thenThrow(new AthleteNotFoundException());

        // When
        ResponseEntity<List<DetailedActivityDto>> responseEntity = activityController.getAthleteLastTenActivities();

        // Then
        Mockito.verify(stravaAccessTokenProvider).getAccessToken(Mockito.anyString());

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}