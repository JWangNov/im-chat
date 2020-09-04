package com.jw.imchatclient.controller;

import com.jw.imchatclient.client.ImChatClient;
import com.jw.imchatcommon.codec.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is only for testing auth procedure now
 * <p>
 * e.g.: http://localhost:8080/test/mock?type=AUTH_REQUEST&message={"accessToken":"jw"}
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ImChatClient imChatClient;

    @PostMapping("/mock")
    public String simulate(String type, String message) {
        Invocation invocation = new Invocation(type, message);
        imChatClient.send(invocation);
        return "success";
    }
}
