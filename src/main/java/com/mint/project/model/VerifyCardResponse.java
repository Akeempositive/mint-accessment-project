package com.mint.project.model;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import javax.validation.Payload;
import java.util.HashMap;
import java.util.Map;

public class VerifyCardResponse implements Serializer {

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
        this.success= false;
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

    public String toString(){
        return (!success )? "{ success:"+ success + ", \"payload\": null}" :
                "{ \"success\":" + success + ", \"payload\" : {\"scheme\" :\"" + payload.get("scheme") +
                "\", \"bank\":\"" + payload.get("bank") +
                        "\", \"type\":\""+ payload.get("type")+ "\"}}";
    }

    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, Object o) {
        return new byte[0];
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Object data) {
        return new byte[0];
    }

    @Override
    public void close() {

    }
}