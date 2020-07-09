package com.jw.imchatcommon.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * {@link Invocation} decoder
 */
@Slf4j
public class InvocationDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> output) {
        input.markReaderIndex();
        if (input.readableBytes() <= 4) return;

        int length = input.readInt();
        if (length < 0) throw new CorruptedFrameException("negative length: " + length);
        if (input.readableBytes() < length) {
            input.resetReaderIndex();
            return;
        }

        byte[] content = new byte[length];
        input.readBytes(content);
        Invocation invocation = JSON.parseObject(content, Invocation.class);
        output.add(invocation);

        log.info("[decode][channel ({}) has decoded a new message ({})", ctx.channel().id(), invocation.toString());
    }
}
