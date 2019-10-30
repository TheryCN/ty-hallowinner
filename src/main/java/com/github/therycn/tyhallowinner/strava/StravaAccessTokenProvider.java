package com.github.therycn.tyhallowinner.strava;

import com.github.therycn.tyhallowinner.entity.Athlete;
import com.github.therycn.tyhallowinner.exception.AthleteNotFoundException;
import com.github.therycn.tyhallowinner.service.AthleteService;
import com.github.therycn.tyhallowinner.strava.dto.RefreshTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class StravaAccessTokenProvider {

    @Autowired
    private AthleteService athleteService;

    @Autowired
    private StravaClient stravaClient;

    public String getAccessToken(String username) throws AthleteNotFoundException {
        String accessToken = "";
        LocalDateTime now = LocalDateTime.now();

        Athlete athlete = athleteService.get(username);
        LocalDateTime tokenExpirationLocalDateTime = LocalDateTime.ofInstant(athlete.getExpiresAt().toInstant(), ZoneId.systemDefault());
        if (now.isAfter(tokenExpirationLocalDateTime)) {
            RefreshTokenDto stravaRefreshToken = stravaClient.refreshToken(athlete.getRefreshToken());
            athlete.updateStravaCredentials(stravaRefreshToken.getRefreshToken(), stravaRefreshToken.getAccessToken(), stravaRefreshToken.getExpiresAt());
            athlete = athleteService.save(athlete);
        }
        accessToken = athlete.getAccessToken();

        return accessToken;
    }

}
