package com.github.therycn.tyhallowinner.strava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.therycn.tyhallowinner.strava.dto.DetailedActivityDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(StravaClient.class)
class StravaClientTest {

    @Autowired
    private StravaClient stravaClient;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void whenGetAthleteLastTenActivities_thenReturnDetailedActivityDtoList() throws JsonProcessingException {
        // Given
        ObjectMapper mapper = new ObjectMapper();
        DetailedActivityDto detailedActivityDto = new DetailedActivityDto(1l, "Running", "", 10000f, 600, null, 100f, 0f, 0f);

        mockServer.expect(requestTo("https://www.strava.com/api/v3/athlete/activities?page=1&per_page=10"))
                .andRespond(withSuccess(mapper.writeValueAsString(Arrays.asList(detailedActivityDto)), MediaType.APPLICATION_JSON));

        // When
        List<DetailedActivityDto> detailedActivityDtoList = stravaClient.getAthleteLastTenActivities("ACCESS_TOKEN");

        // Then
        mockServer.verify();
        Assertions.assertThat(detailedActivityDtoList).containsExactly(detailedActivityDto);
    }

    @Test
    void whenGetAthleteLastTenActivities_thenThrowHttpServerErrorException() {
        // Given
        mockServer.expect(requestTo("https://www.strava.com/api/v3/athlete/activities?page=1&per_page=10"))
                .andRespond(withServerError());

        try {
            // When
            stravaClient.getAthleteLastTenActivities("ACCESS_TOKEN");
            Assertions.fail("Expected HttpServerErrorException");
        } catch (Exception e) {
            // Then
            mockServer.verify();
            Assertions.assertThat(e).isInstanceOf(HttpServerErrorException.class);
        }

    }

}