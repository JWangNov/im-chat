package com.jw.imchatcommon.dispatcher;

import com.jw.imchatcommon.codec.Invocation;
import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<Invocation> {
    // TODO
}
