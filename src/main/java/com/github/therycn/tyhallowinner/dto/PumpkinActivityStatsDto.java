package com.github.therycn.tyhallowinner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PumpkinActivityStatsDto {

    private long id;

    private String name;

    private float distance;

    @JsonProperty("moving_time")
    private int movingTime;

    private float calories;

    private float pumpkinsYouCanEat;

    private float proteins;

    private float carbohydrates;

}
