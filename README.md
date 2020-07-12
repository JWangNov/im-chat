# im chat
 IM chatting room based on Netty

## API
_(for detailed info, see [Usage.md](/document/Usage.md))_
### Authentication
POST Params:
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
POST Params:
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
POST Params:
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
