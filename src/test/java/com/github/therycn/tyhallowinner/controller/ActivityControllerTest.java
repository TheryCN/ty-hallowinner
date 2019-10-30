package com.github.therycn.tyhallowinner.controller;

import com.github.therycn.tyhallowinner.strava.StravaAccessTokenProvider;
import com.github.therycn.tyhallowinner.strava.StravaClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

}