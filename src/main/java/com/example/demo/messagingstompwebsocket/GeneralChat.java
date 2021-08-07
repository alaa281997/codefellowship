package com.example.demo.messagingstompwebsocket;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class GeneralChat {
    private String content;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    public GeneralChat() {
    }


    public GeneralChat(String content) {
        this.content = content;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
