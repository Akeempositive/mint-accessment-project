package com.mint.project.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                logger.debug(String.format("Response Status Code: %s", response.getRawStatusCode()));
                logger.debug(String.format("Response Status Text: %s", response.getStatusText()));
                if (response.getRawStatusCode() == 404 || response.getRawStatusCode() == 502 || response.getRawStatusCode() == 503 || response.getRawStatusCode() == 504) {
                    return true;
                }
                return false; // we want to be able to parse the error response
            }
        });
        return restTemplate;
    }

}