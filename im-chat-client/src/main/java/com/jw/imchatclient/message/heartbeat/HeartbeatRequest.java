package com.jw.imchatclient.message.heartbeat;

import com.jw.imchatcommon.dispatcher.Message;

public class HeartbeatRequest implements Message {

    public static final String TYPE = "HEARTBEAT_REQUEST";

    @Override
    public String toString() {
        return "HeartbeatRequest{}";
    }
}