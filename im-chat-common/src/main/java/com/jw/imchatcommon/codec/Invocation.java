package com.jw.imchatcommon.codec;

import com.alibaba.fastjson.JSON;
import com.jw.imchatcommon.dispatcher.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Message body
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Invocation {

    private String type;
    // message is in json format
    private String message;

    public Invocation(String type, Message message) {
        this.type = type;
        this.message = JSON.toJSONString(message);
    }

    // --- special setters for streaming

    public Invocation setType(String type) {
        this.type = type;
        return this;
    }

    public Invocation setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Invocation{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
