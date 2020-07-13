package com.jw.imchatcommon.dispatcher;

import com.jw.imchatcommon.message.Message;
import io.netty.channel.Channel;

public interface MessageHandler<T extends Message> {

    void execute(Channel channel, T message);

    String getType();
}
