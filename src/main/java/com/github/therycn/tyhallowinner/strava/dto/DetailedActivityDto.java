package com.github.therycn.tyhallowinner.strava.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DetailedActivityDto {

    /**
     * The unique identifier of the activity.
     */
    private long id;

    /**
     * The name of the activity.
     */
    private String name;

    private String description;

    /**
     * The activity's distance, in meters.
     */
    private float distance;

    /**
     * The activity's moving time, in seconds.
     */
    @JsonProperty("moving_time")
    private int movingTime;

    /**
     * The time at which the activity was started.
     */
    @JsonProperty("start_date")
    private Date startDate;

    /**
     * The activity's highest elevation, in meters.
     */
    @JsonProperty("elev_high")
    private float elevHigh;

    /**
     * The activity's lowest elevation, in meters.
     */
    @JsonProperty("elev_low")
    private float elevLow;

    /**
     * The number of kilocalories consumed during this activity.
     */
    private float calories;

}
