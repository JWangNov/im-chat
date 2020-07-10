package com.jw.imchatserver.message.auth;

import com.jw.imchatcommon.dispatcher.Message;
import lombok.Getter;

@Getter
public class AuthResponse implements Message {

    public static final String TYPE = "AUTH_RESPONSE";

    private Integer code;
    private String message;

    // --- special setters for streaming

    public AuthResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public AuthResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    // --- special toString to fit json format

    @Override
    public String toString() {
        return "AuthResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
