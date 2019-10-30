package com.github.therycn.tyhallowinner.strava.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetailedActivityDto {

    /** The unique identifier of the activity. */
    private long id;

    /** The name of the activity. */
    private String name;

    /** The activity's distance, in meters. */
    private float distance;

    /** The activity's moving time, in seconds. */
    @JsonProperty("moving_time")
    private int movingTime;

    /** The time at which the activity was started. */
    @JsonProperty("start_date")
    private Date startDate;

    /** The activity's highest elevation, in meters. */
    @JsonProperty("elev_high")
    private float elevHigh;

    /** The activity's lowest elevation, in meters. */
    @JsonProperty("elev_low")
    private float elevLow;

}
