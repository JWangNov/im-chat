# Usage

## Setup
```shell script
mvn clean compile
```

- **server**
    - entry class: [ImChatServerApplication](/im-chat-server/src/main/java/com/jw/imchatserver/ImChatServerApplication.java)
    - argv: `<empty>`
    - port: `8888` _(by default)_
- **client A**
    - entry class: [ImChatClientApplication](/im-chat-client/src/main/java/com/jw/imchatclient/ImChatClientApplication.java)
    - argv: `<empty>`
    - port: `8080` _(by default)_
- **client B**
    - entry class: [ImChatClientApplication](/im-chat-client/src/main/java/com/jw/imchatclient/ImChatClientApplication.java)
    - argv: `--server.port=8081`
    - port: `8081`
- **client C**
    - entry class: [ImChatClientApplication](/im-chat-client/src/main/java/com/jw/imchatclient/ImChatClientApplication.java)
    - argv: `--server.port=8082`
    - port: `8082`

## Basic Connection & Heartbeat
### steps
1. start **server**
1. start **client A**
1. wait 60s
### log
```
// server
2020-07-12 16:33:47.917  INFO 80873 --- [ntLoopGroup-3-1] c.j.i.s.handler.ImChatChannelManager     : [add][add new channel c0c84eb5]
2020-07-12 16:34:48.033  INFO 80873 --- [ntLoopGroup-3-1] c.j.i.codec.InvocationDecoder            : [decode][channel (c0c84eb5) has decoded a new message (Invocation{type='HEARTBEAT_REQUEST', message='{}'})]
2020-07-12 16:34:48.036  INFO 80873 --- [pool-1-thread-1] c.j.i.m.h.HeartbeatRequestHandler        : [execute][receive heartbeat request from channel (c0c84eb5)]
2020-07-12 16:34:48.047  INFO 80873 --- [ntLoopGroup-3-1] c.j.i.codec.InvocationEncoder            : [encode][channel (c0c84eb5) has encoded a message (Invocation{type='HEARTBEAT_RESPONSE', message='{}'})]

// client
2020-07-12 16:34:47.908  INFO 80894 --- [ntLoopGroup-2-1] c.j.i.c.handler.ImChatClientHandler      : [userEventTriggered][triggered a heartbeat]
2020-07-12 16:34:47.971  INFO 80894 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationEncoder            : [encode][channel (56f02aab) has encoded a message (Invocation{type='HEARTBEAT_REQUEST', message='{}'})]
2020-07-12 16:34:48.054  INFO 80894 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationDecoder            : [decode][channel (56f02aab) has decoded a new message (Invocation{type='HEARTBEAT_RESPONSE', message='{}'})]
2020-07-12 16:34:48.056  INFO 80894 --- [pool-1-thread-1] c.j.i.m.h.HeartbeatResponseHandler       : [execute][receive heartbeat response from channel (56f02aab)]
```

## Authentication
### POST params
```json
{
  "type":"AUTH_REQUEST",
  "message":
    {
      "accessToken":"user token"
    }
}
```
### steps
1. start **server**
1. start **client A**
    - `curl -d 'type=AUTH_REQUEST&message={"accessToken":"alfa"}' http://localhost:8080/test/simulate`
### log
```
// server
2020-07-12 16:43:02.429  INFO 83010 --- [ntLoopGroup-3-1] c.j.i.s.handler.ImChatChannelManager     : [add][add new channel 1360e25d]
2020-07-12 16:43:07.378  INFO 83010 --- [ntLoopGroup-3-1] c.j.i.codec.InvocationDecoder            : [decode][channel (1360e25d) has decoded a new message (Invocation{type='AUTH_REQUEST', message='{"accessToken":"alfa"}'})]
2020-07-12 16:43:07.393  INFO 83010 --- [ntLoopGroup-3-1] c.j.i.codec.InvocationEncoder            : [encode][channel (1360e25d) has encoded a message (Invocation{type='AUTH_RESPONSE', message='{"code":0}'})]

// client
2020-07-12 16:43:07.312  INFO 83045 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationEncoder            : [encode][channel (3514043a) has encoded a message (Invocation{type='AUTH_REQUEST', message='{"accessToken":"alfa"}'})]
2020-07-12 16:43:07.400  INFO 83045 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationDecoder            : [decode][channel (3514043a) has decoded a new message (Invocation{type='AUTH_RESPONSE', message='{"code":0}'})]
2020-07-12 16:43:07.402  INFO 83045 --- [pool-1-thread-1] c.j.i.m.auth.AuthResponseHandler         : [execute][auth result: AuthResponse{code=0, message='null'}]
```

## Send Private Chat
from **client A** to **client B**
### POST params
```json
{
  "type":"CHAT_SEND_TO_ONE_REQUEST",
  "message":
    {
      "toUser":"bravo",
      "msgId":"1",
      "content":"hello"
    }
}
```
### steps
1. start **server**
1. start **client A**
    - `curl -d 'type=AUTH_REQUEST&message={"accessToken":"alfa"}' http://localhost:8080/test/simulate`
1. start **client B**
    - `curl -d 'type=AUTH_REQUEST&message={"accessToken":"bravo"}' http://localhost:8081/test/simulate`
1. send a message from **A** to **B**
    - `curl -d 'type=CHAT_SEND_TO_ONE_REQUEST&message={toUser:"bravo",msgId:"1",content:"hello"}' http://localhost:8080/test/simulate`
### log
```
// server
2020-07-12 16:56:31.014  INFO 85820 --- [ntLoopGroup-3-1] c.j.i.codec.InvocationDecoder            : [decode][channel (d661c962) has decoded a new message (Invocation{type='CHAT_SEND_TO_ONE_REQUEST', message='{toUser:"bravo",msgId:"1",content:"hello"}'})]
2020-07-12 16:56:31.015  INFO 85820 --- [ntLoopGroup-3-1] c.j.i.codec.InvocationEncoder            : [encode][channel (d661c962) has encoded a message (Invocation{type='CHAT_SEND_RESPONSE', message='{"code":0,"msgId":"1"}'})]
2020-07-12 16:56:31.015  INFO 85820 --- [ntLoopGroup-3-2] c.j.i.codec.InvocationEncoder            : [encode][channel (ea9a635f) has encoded a message (Invocation{type='CHAT_REDIRECT_TO_USER_REQUEST', message='{"content":"hello","msgId":"1"}'})]

// client A
2020-07-12 16:56:31.013  INFO 85842 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationEncoder            : [encode][channel (7fae6d84) has encoded a message (Invocation{type='CHAT_SEND_TO_ONE_REQUEST', message='{toUser:"bravo",msgId:"1",content:"hello"}'})]
2020-07-12 16:56:31.015  INFO 85842 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationDecoder            : [decode][channel (7fae6d84) has decoded a new message (Invocation{type='CHAT_SEND_RESPONSE', message='{"code":0,"msgId":"1"}'})]
2020-07-12 16:56:31.016  INFO 85842 --- [pool-1-thread-3] c.j.i.m.chat.ChatSendResponseHandler     : [execute][chat send response: ChatSendResponse{msgId='1', code=0, message='null'}]

// client B
2020-07-12 16:56:31.015  INFO 85867 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationDecoder            : [decode][channel (09d834f2) has decoded a new message (Invocation{type='CHAT_REDIRECT_TO_USER_REQUEST', message='{"content":"hello","msgId":"1"}'})]
2020-07-12 16:56:31.016  INFO 85867 --- [pool-1-thread-3] j.i.m.c.ChatRedirectToUserRequestHandler : [execute][receive message: ChatRedirectToUserRequest{msgId='1', content='hello'}]
```

## Send Message to All
from **client A** to all clients
### POST params
```json
{
  "type":"CHAT_SEND_TO_ALL_REQUEST",
  "message":
    {
      "msgId":"2",
      "content":"hey all"
    }
}
```
### steps
1. start **server**
1. start **client A**
    - `curl -d 'type=AUTH_REQUEST&message={"accessToken":"alfa"}' http://localhost:8080/test/simulate`
1. start **client B**
    - `curl -d 'type=AUTH_REQUEST&message={"accessToken":"bravo"}' http://localhost:8081/test/simulate`
1. start **client C**
    - `curl -d 'type=AUTH_REQUEST&message={"accessToken":"charlie"}' http://localhost:8082/test/simulate`
1. send a message from **A** to all (include **A** per se)
    - `curl -d 'type=CHAT_SEND_TO_ALL_REQUEST&message={msgId:"2",content:"hey all"}' http://localhost:8080/test/simulate`
### log
```
// server
2020-07-12 17:06:49.396  INFO 88193 --- [ntLoopGroup-3-1] c.j.i.codec.InvocationDecoder            : [decode][channel (c733f42b) has decoded a new message (Invocation{type='CHAT_SEND_TO_ALL_REQUEST', message='{msgId:"2",content:"hey all"}'})]
2020-07-12 17:06:49.401  INFO 88193 --- [ntLoopGroup-3-1] c.j.i.codec.InvocationEncoder            : [encode][channel (c733f42b) has encoded a message (Invocation{type='CHAT_SEND_RESPONSE', message='{"code":0,"msgId":"2"}'})]
2020-07-12 17:06:49.403  INFO 88193 --- [ntLoopGroup-3-3] c.j.i.codec.InvocationEncoder            : [encode][channel (1b89152f) has encoded a message (Invocation{type='CHAT_REDIRECT_TO_USER_REQUEST', message='{"content":"hey all","msgId":"2"}'})]
2020-07-12 17:06:49.403  INFO 88193 --- [ntLoopGroup-3-2] c.j.i.codec.InvocationEncoder            : [encode][channel (fd19ce39) has encoded a message (Invocation{type='CHAT_REDIRECT_TO_USER_REQUEST', message='{"content":"hey all","msgId":"2"}'})]
2020-07-12 17:06:49.403  INFO 88193 --- [ntLoopGroup-3-1] c.j.i.codec.InvocationEncoder            : [encode][channel (c733f42b) has encoded a message (Invocation{type='CHAT_REDIRECT_TO_USER_REQUEST', message='{"content":"hey all","msgId":"2"}'})]

// client A
2020-07-12 17:06:49.395  INFO 88207 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationEncoder            : [encode][channel (542e9597) has encoded a message (Invocation{type='CHAT_SEND_TO_ALL_REQUEST', message='{msgId:"2",content:"hey all"}'})]
2020-07-12 17:06:49.401  INFO 88207 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationDecoder            : [decode][channel (542e9597) has decoded a new message (Invocation{type='CHAT_SEND_RESPONSE', message='{"code":0,"msgId":"2"}'})]
2020-07-12 17:06:49.404  INFO 88207 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationDecoder            : [decode][channel (542e9597) has decoded a new message (Invocation{type='CHAT_REDIRECT_TO_USER_REQUEST', message='{"content":"hey all","msgId":"2"}'})]
2020-07-12 17:06:49.406  INFO 88207 --- [pool-1-thread-3] j.i.m.c.ChatRedirectToUserRequestHandler : [execute][receive message: ChatRedirectToUserRequest{msgId='2', content='hey all'}]
2020-07-12 17:06:49.403  INFO 88207 --- [pool-1-thread-2] c.j.i.m.chat.ChatSendResponseHandler     : [execute][chat send response: ChatSendResponse{msgId='2', code=0, message='null'}]

// client B
2020-07-12 17:06:49.404  INFO 88230 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationDecoder            : [decode][channel (18b86ba6) has decoded a new message (Invocation{type='CHAT_REDIRECT_TO_USER_REQUEST', message='{"content":"hey all","msgId":"2"}'})]
2020-07-12 17:06:49.406  INFO 88230 --- [pool-1-thread-2] j.i.m.c.ChatRedirectToUserRequestHandler : [execute][receive message: ChatRedirectToUserRequest{msgId='2', content='hey all'}]

// client C
2020-07-12 17:06:49.403  INFO 88236 --- [ntLoopGroup-2-1] c.j.i.codec.InvocationDecoder            : [decode][channel (e190ee80) has decoded a new message (Invocation{type='CHAT_REDIRECT_TO_USER_REQUEST', message='{"content":"hey all","msgId":"2"}'})]
2020-07-12 17:06:49.405  INFO 88236 --- [pool-1-thread-2] j.i.m.c.ChatRedirectToUserRequestHandler : [execute][receive message: ChatRedirectToUserRequest{msgId='2', content='hey all'}]
```
