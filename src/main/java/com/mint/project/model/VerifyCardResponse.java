package com.mint.project.model;

import javax.validation.Payload;
import java.util.HashMap;
import java.util.Map;

public class VerifyCardResponse {

    private boolean success;

    private Map<String, String> payload;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, String> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }

    public VerifyCardResponse(){

    }

    public VerifyCardResponse(CardVerifyFromApi cardVerifyFromApi){
        if(cardVerifyFromApi.isSuccess()){
            this.success = true;
            Map<String, String> payload = new HashMap<>();
            payload.put("scheme", cardVerifyFromApi.getScheme());
            payload.put("type", cardVerifyFromApi.getType());
            if(cardVerifyFromApi.getBank()!= null){
                payload.put("bank", cardVerifyFromApi.getBank().getName());
            }
            this.payload = payload;
        }else {
            this.success = false;
        }
    }
}