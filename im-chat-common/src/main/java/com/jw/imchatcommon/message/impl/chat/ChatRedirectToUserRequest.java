package com.jw.imchatcommon.message.impl.chat;

import com.jw.imchatcommon.message.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ChatRedirectToUserRequest implements Message {

    public static final String TYPE = "CHAT_REDIRECT_TO_USER_REQUEST";

    private String msgId;
    private String content;
    // TODO: maybe need to add field "fromUser".. tbd

    // --- special toString to fit json format

    @Override
    public String toString() {
        return "ChatRedirectToUserRequest{" +
                "msgId='" + msgId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
