package com.mint.project.service;

import com.mint.project.model.VerifyCardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.jvm.hotspot.utilities.Assert;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardServiceTest {

    @Autowired
    private CardService cardService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void verifyCardByCardNumberFrom3rdPartyApi() {
        VerifyCardResponse verifyCardResponse = cardService.verifyCardByCardNumberFrom3rdPartyApi("539923");
        Assert.that(verifyCardResponse.isSuccess(), "The API is not accessible. Third party api not valid/bad network connection");
        Map<String, String> payload = verifyCardResponse.getPayload();
        Assert.that(payload.get("scheme").equals("mastercard"), "The Scheme of 5399 is no longer mastercard. Probably an outdated result expected");
        Assert.that(payload.get("bank").equals("FIRST"), "The BIN of First bank is no longer 539923. Expectation and requirements has changed");
    }

    @Test
    void getHitCounts() {
    }

}