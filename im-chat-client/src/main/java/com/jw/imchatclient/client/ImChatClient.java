package com.jw.imchatclient.client;

import com.jw.imchatclient.client.handler.ImChatClientHandlerInitializer;
import com.jw.imchatcommon.codec.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ImChatClient {

    private static final Integer RECONNECT_SECONDS = 20;

    @Value("${netty.server.host}")
    private String serverHost;
    @Value("${netty.server.port}")
    private Integer serverPort;
    @Autowired
    private ImChatClientHandlerInitializer imChatClientHandlerInitializer;

    // for read/write and connection
    private final EventLoopGroup eventGroup = new NioEventLoopGroup();
    private volatile Channel channel;

    /**
     * start im client
     */
    @PostConstruct
    public void start() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(serverHost, serverPort)
                .option(ChannelOption.SO_KEEPALIVE, true)
                // allow sending small packets, to avoid delay
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(imChatClientHandlerInitializer);

        bootstrap.connect().addListener((ChannelFutureListener) channelFuture -> {
            if (!channelFuture.isSuccess()) {
                log.error("[start][IM Chat Client failed to connect server ({}:{})]", serverHost, serverPort);
                reconnect();
                return;
            }
            channel = channelFuture.channel();
            log.info("[start][IM Chat Client has successfully connected to server ({}:{})]", serverHost, serverPort);
        });
    }

    public void reconnect() {
        eventGroup.schedule(
                () -> {
                    log.info("[reconnect][start to reconnect]");
                    try {
                        start();
                    } catch (InterruptedException e) {
                        log.error("[reconnect][reconnect failed]", e);
                    }
                }
                , RECONNECT_SECONDS
                , TimeUnit.SECONDS
        );
        log.info("[reconnect][will reconnect in {} seconds]", RECONNECT_SECONDS);
    }

    /**
     * shutdown im client
     */
    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.close();
        }
        eventGroup.shutdownGracefully();
    }

    /**
     * send Message through {@link #channel}
     *
     * @param invocation Message
     */
    public void send(Invocation invocation) {
        if (channel == null) {
            log.error("[send][channel does not exist]");
            return;
        }
        if (!channel.isActive()) {
            log.error("[send][channel is not active]");
            return;
        }
        channel.writeAndFlush(invocation);
    }
}
