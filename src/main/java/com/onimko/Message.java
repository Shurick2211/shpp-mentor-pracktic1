package com.onimko;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The POJO-class
 */
public class Message {

    /**
     * The value for serialize.
     */
    @JsonProperty(value = "message")
    private String msg;

    /**
     * The constructor for POJO-class.
     * @param msg The input message.
     */
    public Message(String msg) {
        this.msg = msg;
    }
}
