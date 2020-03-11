package com.mint.project.controller;

import com.mint.project.model.HitCountResponse;
import com.mint.project.model.VerifyCardResponse;
import com.mint.project.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("card-scheme")
public class CardSchemeController {

    @Autowired
    private CardService cardService;

    @RequestMapping(method = RequestMethod.GET, value = "verify/{cardNumber}")
    public void verifyCard(@PathVariable("cardNumber") String cardNumber){
        cardService.publishToKafka(cardNumber);
    }

    @RequestMapping(method = RequestMethod.GET, value = "stats")
    public @ResponseBody HitCountResponse hitCount(@RequestParam("start") int start, @RequestParam("limit") int limit){
        return cardService.getHitCounts(start, limit);
    }
}
