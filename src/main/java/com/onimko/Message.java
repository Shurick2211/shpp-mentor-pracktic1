package com.onimko;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty(value = "message")
    private String msg;

    public Message(String msg) {
        this.msg = msg;
    }
}
