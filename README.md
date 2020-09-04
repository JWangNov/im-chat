# im chat
IM chatting room based on Netty, which has functions of:
- User's identity authentication
- Heartbeat connection & idle detection between server and client
- One-to-one private message
- One-to-all broadcast message

## Structure
```
im-chat
├── im-chat-client      netty client module
├── im-chat-common      netty common: custom protocol, codec, messages, msg dispactch
└── im-chat-server      netty server module
```

### Custom Protocol
To avoid sticky TCP packet and unnecessary unpacking, 
a [custom protocol](/im-chat-common/src/main/java/com/jw/imchatcommon/codec/InvocationEncoder.java) is implemented.

Now each message has 2 parts: a header contains message length & a body contains message content.

```
MSG = [(Integer) MSG.length] + [(Bytes) MSG.content]
```

## API
_(for detailed info, see [Usage.md](/document/Usage.md))_
### Authentication
POST params:
```json
{
  "type":"AUTH_REQUEST",
  "message":
  {
    "accessToken":"user token"
  }
}
```
### Send Private Chat Message
POST params:
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
### Send Broadcast Message to All
POST params:
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

## TODOs
see [TODOs.md](/document/TODOs.md)
