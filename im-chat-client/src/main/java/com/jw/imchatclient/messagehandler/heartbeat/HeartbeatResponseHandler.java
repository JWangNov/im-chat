package com.jw.imchatclient.messagehandler.heartbeat;

import com.jw.imchatcommon.message.impl.heartbeat.HeartbeatResponse;
import com.jw.imchatcommon.dispatcher.MessageHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HeartbeatResponseHandler implements MessageHandler<HeartbeatResponse> {

    @Override
    public void execute(Channel channel, HeartbeatResponse message) {
        log.info("[execute][receive heartbeat response from channel ({})]", channel.id());
    }

    @Override
    public String getType() {
        return HeartbeatResponse.TYPE;
    }
}
