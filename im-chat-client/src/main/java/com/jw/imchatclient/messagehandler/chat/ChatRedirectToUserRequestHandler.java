package com.jw.imchatclient.messagehandler.chat;

import com.jw.imchatcommon.message.impl.chat.ChatRedirectToUserRequest;
import com.jw.imchatcommon.dispatcher.MessageHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChatRedirectToUserRequestHandler implements MessageHandler<ChatRedirectToUserRequest> {

    @Override
    public void execute(Channel channel, ChatRedirectToUserRequest message) {
        log.info("[execute][receive message: {}]", message);
    }

    @Override
    public String getType() {
        return ChatRedirectToUserRequest.TYPE;
    }
}
