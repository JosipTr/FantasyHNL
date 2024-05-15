package com.fantasyhnl.util;

import com.fantasyhnl.exception.InvalidServerApiCallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RestService {
    private static final Logger logger = LoggerFactory.getLogger(RestService.class);

    private final RestTemplate restTemplate;

    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getResponseBody(String path) {
        try {
            var response = restTemplate.getForEntity(path, String.class);
            var result = response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
            logger.info("Rest service result: {} \n", result);
            return result;
        } catch (RestClientException e) {
            throw new InvalidServerApiCallException("Invalid API call");
        }
    }
}