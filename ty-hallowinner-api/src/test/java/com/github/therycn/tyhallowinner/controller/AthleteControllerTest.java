package com.github.therycn.tyhallowinner.controller;

import com.github.therycn.tyhallowinner.dto.AthleteDto;
import com.github.therycn.tyhallowinner.entity.Athlete;
import com.github.therycn.tyhallowinner.service.AthleteService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

class AthleteControllerTest {

    private AthleteController athleteController;

    private AthleteService athleteService;

    @BeforeEach
    void init() {
        athleteService = Mockito.mock(AthleteService.class);
        athleteController = new AthleteController(athleteService);
    }

    @Test
    void whenList_thenReturnAthleteDtoList() {
        // Given
        AthleteDto expectedAthleteDtoThry = new AthleteDto(1L, "thry");
        AthleteDto expectedAthleteDtoZlatan = new AthleteDto(2L, "zlatan");

        Athlete athleteThry = new Athlete(1L, "thry", null, null, null);
        Athlete athleteZlatan = new Athlete(2L, "zlatan", null, null, null);

        Mockito.when(athleteService.list()).thenReturn(Arrays.asList(athleteThry, athleteZlatan));

        // When
        ResponseEntity<List<AthleteDto>> responseEntity = athleteController.list();

        // Then
        Mockito.verify(athleteService).list();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).containsExactly(expectedAthleteDtoThry, expectedAthleteDtoZlatan);
    }
}