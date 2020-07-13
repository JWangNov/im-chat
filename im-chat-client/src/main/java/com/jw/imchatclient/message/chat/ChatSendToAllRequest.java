package com.jw.imchatclient.message.chat;

import com.jw.imchatcommon.message.Message;
import lombok.Getter;

/**
 * This is to send message to all client
 * <p>
 * todo: for group chat, need to add groupId based on this class, also add groupId field into all clients
 */
@Getter
public class ChatSendToAllRequest implements Message {

    public static final String TYPE = "CHAT_SEND_TO_ALL_REQUEST";

    private String msgId;
    private String content;

    // --- special setters for streaming

    public ChatSendToAllRequest setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public ChatSendToAllRequest setContent(String content) {
        this.content = content;
        return this;
    }

    // --- special toString to fit json format

    @Override
    public String toString() {
        return "ChatSendToAllRequest{" +
                "msgId='" + msgId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
