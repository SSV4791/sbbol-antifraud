package ru.sberbank.pprb.sbbol.antifraud.healthindicator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class DataSpaceCoreIndicator implements HealthIndicator {

    private static final String MESSAGE_KEY = "DataSpaceCore";
    private static final String HEALTH_CHECK = "/actuator/health/readiness";

    @Value("${core.url}")
    private String server;

    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpEntity<String> requestEntity = new HttpEntity<>("", new HttpHeaders());


    @Override
    public Health health() {
        if (!isRunningDataSpaceCore()) {
            return Health.down().withDetail(MESSAGE_KEY, "Not Available").build();
        }
        return Health.up().withDetail(MESSAGE_KEY, "Available").build();
    }

    private boolean isRunningDataSpaceCore() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(server + HEALTH_CHECK, HttpMethod.GET, requestEntity, String.class);
        return responseEntity.getStatusCode() == HttpStatus.OK;
    }

}
