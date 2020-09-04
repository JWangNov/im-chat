# TODOs

- [x] deduplicate `message` package (**client** & **server**)
- [ ] replace dependency `fastjson` by simply `jackson` (**common**)
- [ ] replace custom protocol by `Google Protocol` & `Netty pipeline` (**common**, don't use `LineBasedFrameDecoder
` cuz there could be some multi-line messages)
- [ ] add `fromUser` into `ChatRedirectToUserRequest` (**common**)
