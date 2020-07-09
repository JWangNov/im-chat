package com.jw.imchatserver.server.handler;

import com.jw.imchatcommon.codec.Invocation;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This is for:
 * ... client Channel management
 * ... sending message to client Channel
 */
@Component
@Slf4j
public class NettyChannelManager {

    private static final AttributeKey<String> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance("user");

    private final ConcurrentMap<ChannelId, Channel> channelConcurrentMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Channel> userChannelConcurrentMap = new ConcurrentHashMap<>();

    /**
     * add Channel into {@link #channelConcurrentMap}
     *
     * @param channel Channel
     */
    public void add(Channel channel) {
        channelConcurrentMap.put(channel.id(), channel);
        log.info("[add][add new channel {}", channel.id());
    }

    /**
     * add User into {@link #userChannelConcurrentMap}
     *
     * @param channel Channel
     * @param user    User
     */
    public void addUser(Channel channel, String user) {
        Channel checkChannel = channelConcurrentMap.get(channel.id());
        if (checkChannel == null) {
            log.error("[addUser][channel {} does not exist]", channel.id());
            return;
        }
        channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
        userChannelConcurrentMap.put(user, channel);
    }

    /**
     * remove Channel from {@link #channelConcurrentMap} and {@link #userChannelConcurrentMap}
     *
     * @param channel Channel
     */
    public void remove(Channel channel) {
        channelConcurrentMap.remove(channel.id());
        if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)) {
            userChannelConcurrentMap.remove(channel.attr(CHANNEL_ATTR_KEY_USER).get());
        }
        log.info("[remove][channel {} has been removed]", channel.id());
    }

    /**
     * send Message to User
     *
     * @param user       User
     * @param invocation Message
     */
    public void send(String user, Invocation invocation) {
        var channel = userChannelConcurrentMap.get(user);
        if (channel == null) {
            log.error("[send][channel does not exist]");
            return;
        }
        if (!channel.isActive()) {
            log.error("[send][channel {} is not active]", channel.id());
            return;
        }
        channel.writeAndFlush(invocation);
    }

    /**
     * send Message to all users
     *
     * @param invocation Message
     */
    public void sendAll(Invocation invocation) {
        for (Channel channel : channelConcurrentMap.values()) {
            if (!channel.isActive()) {
                log.error("[send][channel {} is not active]", channel.id());
                return;
            }
            channel.writeAndFlush(invocation);
        }
    }
}
