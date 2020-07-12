package com.jw.imchatclient.client.handler;

import com.jw.imchatcommon.codec.InvocationDecoder;
import com.jw.imchatcommon.codec.InvocationEncoder;
import com.jw.imchatcommon.dispatcher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImChatClientHandlerInitializer extends ChannelInitializer<Channel> {

    private static final Integer READ_TIMEOUT_SECONDS = 3 * 60;
    private static final Integer READER_IDLE_TIME_SECONDS = 60;

    @Autowired
    private MessageDispatcher messageDispatcher;
    @Autowired
    private ImChatClientHandler imChatClientHandler;

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                // check idle & timeout
                .addLast(new IdleStateHandler(READER_IDLE_TIME_SECONDS, 0, 0))
                .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS))
                // add encoder and decoder
                .addLast(new InvocationEncoder())
                .addLast(new InvocationDecoder())
                .addLast(messageDispatcher)
                .addLast(imChatClientHandler)
        ;
    }
}
