package com.jw.imchatserver.messagehandler.heartbeat;

import com.jw.imchatcommon.codec.Invocation;
import com.jw.imchatcommon.dispatcher.MessageHandler;
import com.jw.imchatserver.message.heartbeat.HeartbeatRequest;
import com.jw.imchatserver.message.heartbeat.HeartbeatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HeartbeatRequestHandler implements MessageHandler<HeartbeatRequest> {

    @Override
    public void execute(Channel channel, HeartbeatRequest message) {
        log.info("[execute][receive heartbeat request from channel ({})]", channel.id());
        HeartbeatResponse response = new HeartbeatResponse();
        channel.writeAndFlush(new Invocation(HeartbeatResponse.TYPE, response));
    }

    @Override
    public String getType() {
        return HeartbeatRequest.TYPE;
    }
}
