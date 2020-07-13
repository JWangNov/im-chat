package com.jw.imchatcommon.message.impl.chat;

import com.jw.imchatcommon.message.Message;
import lombok.Getter;

@Getter
public class ChatSendToOneRequest implements Message {

    public static final String TYPE = "CHAT_SEND_TO_ONE_REQUEST";

    private String toUser;
    private String msgId;
    private String content;

    // --- special setters for streaming

    public ChatSendToOneRequest setToUser(String toUser) {
        this.toUser = toUser;
        return this;
    }

    public ChatSendToOneRequest setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public ChatSendToOneRequest setContent(String content) {
        this.content = content;
        return this;
    }

    // --- special toString to fit json format

    @Override
    public String toString() {
        return "ChatSendToOneRequest{" +
                "toUser='" + toUser + '\'' +
                ", msgId='" + msgId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
