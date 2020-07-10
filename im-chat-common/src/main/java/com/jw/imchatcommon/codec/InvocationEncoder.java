package com.jw.imchatcommon.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link Invocation} encoder
 */
@Slf4j
public class InvocationEncoder extends MessageToByteEncoder<Invocation> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Invocation input, ByteBuf output) {
        byte[] content = JSON.toJSONBytes(input);
        output.writeInt(content.length);
        output.writeBytes(content);

        log.info("[encode][channel ({}) has encoded a message ({})]", ctx.channel().id(), input.toString());
    }
}
