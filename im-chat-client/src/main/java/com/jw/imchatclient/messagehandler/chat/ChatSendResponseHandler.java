package com.jw.imchatclient.messagehandler.chat;

import com.jw.imchatcommon.message.impl.chat.ChatSendResponse;
import com.jw.imchatcommon.dispatcher.MessageHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChatSendResponseHandler implements MessageHandler<ChatSendResponse> {

    @Override
    public void execute(Channel channel, ChatSendResponse message) {
        log.info("[execute][chat send response: {}]", message);
    }

    @Override
    public String getType() {
        return ChatSendResponse.TYPE;
    }
}
