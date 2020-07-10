package com.jw.imchatclient.message.auth;

import com.jw.imchatcommon.dispatcher.Message;
import lombok.Getter;

@Getter
public class AuthRequest implements Message {

    public static final String TYPE = "AUTH_REQUEST";

    private String accessToken;

    // --- special setters for streaming

    public AuthRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    // --- special toString to fit json format

    @Override
    public String toString() {
        return "AuthRequest{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }
}
