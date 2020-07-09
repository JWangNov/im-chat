package com.jw.imchatserver.server.handler;

import com.jw.imchatcommon.codec.InvocationDecoder;
import com.jw.imchatcommon.codec.InvocationEncoder;
import com.jw.imchatcommon.dispatcher.MessageDispatcher;
import com.jw.imchatserver.server.NettyServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

    private static final Integer READ_TIMEOUT_SECONDS = 3 * 60;

    @Autowired
    private MessageDispatcher messageDispatcher;
    @Autowired
    private NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline
                .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS))
                .addLast(new InvocationEncoder())
                .addLast(new InvocationDecoder())
                .addLast(messageDispatcher)
                .addLast(nettyServerHandler);
    }
}
