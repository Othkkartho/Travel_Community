package com.learn.travel_community.dto;

public class BasicResponse {
    public boolean status;
    public String data;
    public Object object;

    public BasicResponse() {
        this.status = true;
        this.data = "";
        this.object = null;
    }

    public BasicResponse(boolean status, String data, Object object) {
        this.status = status;
        this.data = data;
        this.object = object;
    }
}