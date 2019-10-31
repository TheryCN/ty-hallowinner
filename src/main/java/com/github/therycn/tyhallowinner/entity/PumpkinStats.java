package com.github.therycn.tyhallowinner.entity;

import lombok.*;

import java.text.MessageFormat;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PumpkinStats {

    private float pumpkinsYouCanEat;

    private float proteins;

    private float carbohydrates;

    public String beautify() {
        return MessageFormat.format("You can eat {0} pumkin(s) for {1}g proteins and {2}g carbohydrates",
                pumpkinsYouCanEat, proteins, carbohydrates);
    }

}
