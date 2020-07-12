package com.jw.imchatserver.messagehandler.chat;

import com.jw.imchatcommon.codec.Invocation;
import com.jw.imchatcommon.dispatcher.MessageHandler;
import com.jw.imchatserver.message.chat.ChatRedirectToUserRequest;
import com.jw.imchatserver.message.chat.ChatSendResponse;
import com.jw.imchatserver.message.chat.ChatSendToOneRequest;
import com.jw.imchatserver.server.handler.ImChatChannelManager;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatSendToOneHandler implements MessageHandler<ChatSendToOneRequest> {

    @Autowired
    private ImChatChannelManager channelManager;

    @Override
    public void execute(Channel channel, ChatSendToOneRequest message) {
        ChatSendResponse sendResponse = new ChatSendResponse()
                .setMsgId(message.getMsgId())
                .setCode(0);
        channel.writeAndFlush(new Invocation(ChatSendResponse.TYPE, sendResponse));

        ChatRedirectToUserRequest redirectToUserRequest = new ChatRedirectToUserRequest()
                .setMsgId(message.getMsgId())
                .setContent(message.getContent());
        channelManager.send(
                message.getToUser(),
                new Invocation(ChatRedirectToUserRequest.TYPE, redirectToUserRequest)
        );
    }

    @Override
    public String getType() {
        return ChatSendToOneRequest.TYPE;
    }
}
