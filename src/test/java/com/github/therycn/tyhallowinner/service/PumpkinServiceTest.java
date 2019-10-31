package com.github.therycn.tyhallowinner.service;

import com.github.therycn.tyhallowinner.entity.PumpkinStats;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PumpkinServiceTest {

    private PumpkinService pumpkinService;

    @BeforeEach
    public void init() {
        pumpkinService = new PumpkinService();
    }

    @Test
    void whenGetPumpkinStatsByCalories_thenReturnStats() {
        // Given
        PumpkinStats expectedPumpkinStatsByCalories = new PumpkinStats(2.5407693f, 127.03847f, 825.75006f);

        // When
        PumpkinStats pumpkinStatsByCalories = pumpkinService.getPumpkinStatsByCalories(3303.0f);

        // Then
        Assertions.assertThat(pumpkinStatsByCalories).isEqualTo(expectedPumpkinStatsByCalories);
    }
}