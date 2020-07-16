package com.jw.imchatcommon.codec;

import com.alibaba.fastjson.JSON;
import com.jw.imchatcommon.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Message body
 * <p>
 * For encoding ({@link InvocationEncoder}),
 * each {@link Invocation} is serialized in this format:
 * [length] + [content]
 * <p>
 * This is to avoid unnecessary TCP sticking and unpacking
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Invocation {

    private String type;
    // message is in json format
    private String message;

    public Invocation(String type, Message message) {
        this.type = type;
        this.message = JSON.toJSONString(message);
    }

    // --- special toString to fit json format

    @Override
    public String toString() {
        return "Invocation{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
