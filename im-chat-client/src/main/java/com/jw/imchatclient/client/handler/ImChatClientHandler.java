package com.jw.imchatclient.client.handler;

import com.jw.imchatclient.client.ImChatClient;
import com.jw.imchatclient.message.heartbeat.HeartbeatRequest;
import com.jw.imchatcommon.codec.Invocation;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
@Slf4j
public class ImChatClientHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private ImChatClient imChatClient;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        imChatClient.reconnect();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("[exceptionCaught][exception thrown on channel ({})]", ctx.channel().id(), cause);
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            log.info("[userEventTriggered][triggered a heartbeat]");
            HeartbeatRequest heartbeatRequest = new HeartbeatRequest();
            ctx.writeAndFlush(new Invocation(HeartbeatRequest.TYPE, heartbeatRequest))
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
