package com.jw.imchatcommon.message.impl.chat;

import com.jw.imchatcommon.message.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This is to send message to all client
 * <p>
 * todo: for group chat, need to add groupId based on this class, also add groupId field into all clients
 */
@Getter
@Setter
@Accessors(chain = true)
public class ChatSendToAllRequest implements Message {

    public static final String TYPE = "CHAT_SEND_TO_ALL_REQUEST";

    private String msgId;
    private String content;

    @Override
    public String toString() {
        return "ChatSendToAllRequest{" +
                "msgId='" + msgId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
