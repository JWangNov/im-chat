# im chat
IM chatting room based on Netty

## Structure
```
im-chat
├── im-chat-client      netty client module
├── im-chat-common      netty common: custom protocol, codec, messages, msg dispactch
└── im-chat-server      netty server module
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
### Send Private Chat
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
### Send Message to All
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
