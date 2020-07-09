package com.jw.imchatcommon.dispatcher;

import com.alibaba.fastjson.JSON;
import com.jw.imchatcommon.codec.Invocation;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<Invocation> {

    @Autowired
    private MessageHandlerContainer messageHandlerContainer;

    private final ExecutorService executorService = Executors.newFixedThreadPool(200);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Invocation invocation) throws Exception {
        MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(invocation.getType());
        Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);
        Message message = JSON.parseObject(invocation.getMessage(), messageClass);
        executorService.submit(() -> messageHandler.execute(ctx.channel(), message));
    }
}
