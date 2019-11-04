package com.github.therycn.tyhallowinner.service;

import com.github.therycn.tyhallowinner.entity.PumpkinStats;
import org.springframework.stereotype.Service;

@Service
public class PumpkinService {

    public PumpkinStats getPumpkinStatsByCalories(float calories) {
        // 1 Pumpkin = 5kg, 100g => 26 kcalories, 5kg => 1300 kcalories
        float pumpkinsYouCanEat = calories / 1300;
        // 100g => 1g proteins, 5kg => 50g proteins
        float proteins = pumpkinsYouCanEat * 50;
        // 100g => 6,5g carbohydrate, 5kg => 325g
        float carbohydrates = pumpkinsYouCanEat * 325;

        return new PumpkinStats(pumpkinsYouCanEat, proteins, carbohydrates);
    }
}
