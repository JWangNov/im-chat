package com.jw.imchatserver.server;

import com.jw.imchatserver.server.handler.NettyChannelManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private NettyChannelManager channelManager;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        channelManager.add(ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        channelManager.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("[exceptionCaught][exception occur in connection {}]", ctx.channel().id(), cause);
        ctx.channel().close();
    }
}
