package com.jw.imchatserver.message.chat;

import com.jw.imchatcommon.message.Message;
import lombok.Getter;

@Getter
public class ChatSendResponse implements Message {

    public static final String TYPE = "CHAT_SEND_RESPONSE";

    private String msgId;
    private Integer code;
    private String message;

    // --- special setters for streaming

    public ChatSendResponse setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public ChatSendResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public ChatSendResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    // --- special toString to fit json format

    @Override
    public String toString() {
        return "ChatSendResponse{" +
                "msgId='" + msgId + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
