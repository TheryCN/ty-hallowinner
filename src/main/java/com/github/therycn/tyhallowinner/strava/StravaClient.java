package com.github.therycn.tyhallowinner.strava;

import com.github.therycn.tyhallowinner.exception.ActivityNotFoundException;
import com.github.therycn.tyhallowinner.strava.dto.DetailedActivityDto;
import com.github.therycn.tyhallowinner.strava.dto.RefreshTokenDto;
import com.github.therycn.tyhallowinner.strava.dto.UpdatableActivityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class StravaClient {

    @Value("${strava.client.id}")
    private String clientId;

    @Value("${strava.client.secret}")
    private String clientSecret;

    @Value("${strava.grant.type}")
    private String grantType;

    @Autowired
    private RestTemplate restTemplate;

    public RefreshTokenDto refreshToken(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", grantType);
        map.add("refresh_token", refreshToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<RefreshTokenDto> stravaRefreshTokenResponseEntity = restTemplate.postForEntity("https://www.strava.com/api/v3/oauth/token", request, RefreshTokenDto.class);

        return stravaRefreshTokenResponseEntity.getBody();
    }

    public List<DetailedActivityDto> getAthleteLastTenActivities(String accessToken) {
        ParameterizedTypeReference<List<DetailedActivityDto>> parameterizedTypeReference = new ParameterizedTypeReference<List<DetailedActivityDto>>() {
        };
        HttpHeaders headers = getHttpHeaders(accessToken);
        ResponseEntity<List<DetailedActivityDto>> responseEntity = restTemplate.exchange("https://www.strava.com/api/v3/athlete/activities?page=1&per_page=10", HttpMethod.GET, new HttpEntity<String>(headers), parameterizedTypeReference);

        return responseEntity.getBody();
    }

    public DetailedActivityDto getActivityById(String accessToken, long activityId) throws ActivityNotFoundException {
        HttpHeaders headers = getHttpHeaders(accessToken);
        try {
            ResponseEntity<DetailedActivityDto> responseEntity = restTemplate.exchange("https://www.strava.com/api/v3/activities/" + activityId, HttpMethod.GET, new HttpEntity<>(headers), DetailedActivityDto.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new ActivityNotFoundException();
        }
    }

    public DetailedActivityDto updateActivityById(String accessToken, long activityId, UpdatableActivityDto updatableActivityDto) {
        HttpHeaders headers = getHttpHeaders(accessToken);
        ResponseEntity<DetailedActivityDto> responseEntity = restTemplate.exchange("https://www.strava.com/api/v3/activities/" + activityId, HttpMethod.PUT, new HttpEntity<>(updatableActivityDto, headers), DetailedActivityDto.class);

        return responseEntity.getBody();
    }

    private HttpHeaders getHttpHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        return headers;
    }

}
