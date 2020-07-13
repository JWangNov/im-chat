package com.jw.imchatcommon.message.impl.chat;

import com.jw.imchatcommon.message.Message;
import lombok.Getter;

@Getter
public class ChatRedirectToUserRequest implements Message {

    public static final String TYPE = "CHAT_REDIRECT_TO_USER_REQUEST";

    private String msgId;
    private String content;
    // TODO: maybe need to add field "fromUser".. tbd

    // --- special setters for streaming

    public ChatRedirectToUserRequest setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public ChatRedirectToUserRequest setContent(String content) {
        this.content = content;
        return this;
    }

    // --- special toString to fit json format

    @Override
    public String toString() {
        return "ChatRedirectToUserRequest{" +
                "msgId='" + msgId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
