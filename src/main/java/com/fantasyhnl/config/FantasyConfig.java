package com.fantasyhnl.config;

import com.fantasyhnl.util.JsonToObjectMapper;
import com.fantasyhnl.util.RestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FantasyConfig {

    @Value("${spring.security.api-key}")
    private String apiKey;
    @Value("${spring.security.api-value}")
    private String apiValue;
    @Value("${spring.security.uri}")
    private String uri;
    private final ObjectMapper objectMapper;

    public FantasyConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().defaultHeader(
                apiKey,
                apiValue
        ).rootUri(uri).build();
    }

    @Bean
    public JsonToObjectMapper jsonToObjectMapper() {
        return new JsonToObjectMapper(objectMapper);
    }

    @Bean
    public RestService restService() {
        return new RestService(restTemplate());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}