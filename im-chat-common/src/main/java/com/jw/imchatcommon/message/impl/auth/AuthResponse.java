package com.jw.imchatcommon.message.impl.auth;

import com.jw.imchatcommon.message.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AuthResponse implements Message {

    public static final String TYPE = "AUTH_RESPONSE";

    private Integer code;
    private String message;

    // --- special toString to fit json format

    @Override
    public String toString() {
        return "AuthResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
