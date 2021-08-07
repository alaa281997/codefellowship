package com.example.demo.messagingstompwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller

public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GeneralChat greeting(HelloMessage message, Principal p ) throws Exception {
        Thread.sleep(1000);

        GeneralChat greeting = new GeneralChat();
        greeting.setContent(message.getName());



        return new GeneralChat(   HtmlUtils.htmlEscape(message.getName()));

    }


    @GetMapping("/GeneralChat")
    public String messagePage(){

        return "GeneralLivechat";
    }
}
