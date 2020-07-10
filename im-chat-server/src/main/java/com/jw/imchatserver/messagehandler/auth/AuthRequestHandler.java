package com.jw.imchatserver.messagehandler.auth;

import com.jw.imchatcommon.codec.Invocation;
import com.jw.imchatcommon.dispatcher.MessageHandler;
import com.jw.imchatserver.message.auth.AuthRequest;
import com.jw.imchatserver.message.auth.AuthResponse;
import com.jw.imchatserver.server.handler.ImChatChannelManager;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * To handle authentication request from client
 */
@Component
public class AuthRequestHandler implements MessageHandler<AuthRequest> {

    @Autowired
    private ImChatChannelManager channelManager;

    @Override
    public void execute(Channel channel, AuthRequest request) {
        AuthResponse response = new AuthResponse();

        if (StringUtils.isEmpty(request.getAccessToken())) {
            response.setCode(1).setMessage("accessToken is missing");
            channel.writeAndFlush(new Invocation(AuthResponse.TYPE, response));
            return;
        }

        // for now, simply regard accessToken as user
        channelManager.addUser(channel, request.getAccessToken());

        response.setCode(0);
        channel.writeAndFlush(new Invocation(AuthResponse.TYPE, response));
    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }
}
