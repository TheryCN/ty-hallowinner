package com.github.therycn.tyhallowinner.service;

import com.github.therycn.tyhallowinner.controller.ActivityController;
import com.github.therycn.tyhallowinner.entity.Athlete;
import com.github.therycn.tyhallowinner.exception.AthleteNotFoundException;
import com.github.therycn.tyhallowinner.repository.AthleteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AthleteServiceTest {

    private AthleteService athleteService;

    private AthleteRepository athleteRepository;

    @BeforeEach
    public void init(){
        athleteRepository = Mockito.mock(AthleteRepository.class);
        athleteService = new AthleteService(athleteRepository);
    }

    @Test
    public void whenGetAthleteNotExists_thenThrowAthleteNotFoundException() {
        // Given
        String username = "TEST_USER";
        // When
        try {
            athleteService.get(username);
            Assertions.fail("Expected AthleteNotFoundException");
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(AthleteNotFoundException.class);
        }
        Mockito.verify(athleteRepository).findByUsername(username);
    }

    @Test
    public void whenGetAthleteExists_thenReturnAthlete() throws AthleteNotFoundException {
        // Given
        String username = "TEST_USER";
        Athlete expectedAthlete = new Athlete(1l, username, null, null, null);
        Mockito.when(athleteRepository.findByUsername(username)).thenReturn(Optional.of(expectedAthlete));

        // When
        Athlete athlete = athleteService.get(username);
        // Then
        Mockito.verify(athleteRepository).findByUsername(username);
        Assertions.assertThat(athlete).isEqualTo(expectedAthlete);
    }

}