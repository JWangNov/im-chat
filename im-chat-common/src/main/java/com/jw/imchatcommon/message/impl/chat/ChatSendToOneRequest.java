package com.jw.imchatcommon.message.impl.chat;

import com.jw.imchatcommon.message.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ChatSendToOneRequest implements Message {

    public static final String TYPE = "CHAT_SEND_TO_ONE_REQUEST";

    private String toUser;
    private String msgId;
    private String content;

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
