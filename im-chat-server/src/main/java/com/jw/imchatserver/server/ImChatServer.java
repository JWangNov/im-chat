package com.jw.imchatserver.server;

import com.jw.imchatserver.server.handler.ImChatServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@Component
@Slf4j
public class ImChatServer {

    @Value("${netty.port}")
    private Integer port;
    @Autowired
    private ImChatServerHandlerInitializer imChatServerHandlerInitializer;

    private Channel channel;
    /*
     * to accept client connections
     * avoid frequent data reading and writing of connected clients
     *  affecting the connection of new clients
     */
    private EventLoopGroup masterGroup = new NioEventLoopGroup();
    /*
     * to accept client read / write operations
     * can support multiple clients
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();


    @PostConstruct
    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(masterGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                // queue size of server
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // allow sending small packets, to avoid delay
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(imChatServerHandlerInitializer);
        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            channel = future.channel();
            log.info("[start][Netty Server starts at {} port]", port);
        }
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.close();
        }
        masterGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
