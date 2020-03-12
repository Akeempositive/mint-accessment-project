package com.mint.project.service;

import com.mint.project.model.CardVerifyFromApi;
import com.mint.project.model.HitCountFromApi;
import com.mint.project.model.HitCountResponse;
import com.mint.project.model.VerifyCardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class CardService {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private ProducerService producerService;

    @Autowired
    private RestTemplate restTemplate;

    private final String TOPIC = "topic";

    @Value("${third.party.api.url}")
    private String thirdPartyApiUrl;

    public VerifyCardResponse verifyCardByCardNumberFrom3rdPartyApi(String cardNumber){
        String targetUrl = String.format("%s/%s", thirdPartyApiUrl, cardNumber);
        try {
            ResponseEntity<CardVerifyFromApi> cardVerifyFromApi = restTemplate.getForEntity(targetUrl, CardVerifyFromApi.class, new HashMap<>());
            return new VerifyCardResponse(cardVerifyFromApi.getBody());
        }catch(Exception ex){
            logger.error("An error has occured : {}", ex.getMessage());
            return new VerifyCardResponse();
        }
    }

    public VerifyCardResponse publishToKafka(String cardNumber){
        VerifyCardResponse verifyCardResponse = verifyCardByCardNumberFrom3rdPartyApi(cardNumber);
        producerService.sendMessage(verifyCardResponse.toString());
        return verifyCardResponse;
    }

    public HitCountResponse getHitCounts(int start, int limit){
        String targetUrl = String.format("%s/?start=%d&limit=%d", thirdPartyApiUrl, start, limit);
        try {
            ResponseEntity<HitCountFromApi> hitCountFromApi = restTemplate.getForEntity(targetUrl, HitCountFromApi.class, new HashMap<>());
            return new HitCountResponse(hitCountFromApi.getBody());
        }catch(Exception ex){
            logger.error("An error has occured : {}", ex.getMessage());
            return new HitCountResponse();
        }
    }

}
