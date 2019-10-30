package com.github.therycn.tyhallowinner.strava;

import com.github.therycn.tyhallowinner.exception.AthleteNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("strava")
public class StravaAuthController {

    private StravaAccessTokenProvider stravaAccessTokenProvider;

    private ApplicationContext applicationContext;

    public StravaAuthController(StravaAccessTokenProvider stravaAccessTokenProvider) {
        this.stravaAccessTokenProvider = stravaAccessTokenProvider;
    }

    @GetMapping("/{username}")
    public ResponseEntity<String> getAccessToken(@PathVariable String username) {
        try {
            return ResponseEntity.ok().body(stravaAccessTokenProvider.getAccessToken(username));
        } catch (AthleteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
