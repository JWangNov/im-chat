package com.jw.imchatclient.message.heartbeat;

import com.jw.imchatcommon.message.Message;

public class HeartbeatResponse implements Message {

    public static final String TYPE = "HEARTBEAT_RESPONSE";

    @Override
    public String toString() {
        return "HeartbeatResponse{}";
    }
}
