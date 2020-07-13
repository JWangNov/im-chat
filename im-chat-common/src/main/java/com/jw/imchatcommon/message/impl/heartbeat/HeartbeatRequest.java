package com.jw.imchatcommon.message.impl.heartbeat;

import com.jw.imchatcommon.message.Message;

public class HeartbeatRequest implements Message {

    public static final String TYPE = "HEARTBEAT_REQUEST";

    @Override
    public String toString() {
        return "HeartbeatRequest{}";
    }
}
