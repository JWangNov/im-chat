package com.jw.imchatclient.messagehandler.auth;

import com.jw.imchatclient.message.auth.AuthResponse;
import com.jw.imchatcommon.dispatcher.MessageHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * To handle authentication response from server
 */
@Component
@Slf4j
public class AuthResponseHandler implements MessageHandler<AuthResponse> {

    @Override
    public void execute(Channel channel, AuthResponse response) {
        log.info("[execute][auth result: {}]", response);
    }

    @Override
    public String getType() {
        return AuthResponse.TYPE;
    }
}
