package com.mint.project.service;

import com.mint.project.model.CardVerifyFromApi;
import com.mint.project.model.HitCountFromApi;
import com.mint.project.model.HitCountResponse;
import com.mint.project.model.VerifyCardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class CardService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${third.party.api.url}")
    private String thirdPartyApiUrl;

    public VerifyCardResponse verifyCardByCardNumberFrom3rdPartyApi(String cardNumber){
        String targetUrl = String.format("%s/%s", thirdPartyApiUrl, cardNumber);
        ResponseEntity<CardVerifyFromApi> cardVerifyFromApi = restTemplate.getForEntity(targetUrl, CardVerifyFromApi.class, new HashMap<>());
        return new VerifyCardResponse(cardVerifyFromApi.getBody());
    }

    public HitCountResponse getHitCounts(int start, int limit){
        String targetUrl = String.format("%s/?start=%d&limit=%d", thirdPartyApiUrl, start, limit);
        ResponseEntity<HitCountFromApi> hitCountFromApi = restTemplate.getForEntity(targetUrl, HitCountFromApi.class, new HashMap<>());
        return new HitCountResponse(hitCountFromApi.getBody());
    }

}
