package com.dh.chat.model;

/**
 * Created by Juan Zapata on 6/16/2017.
 */
public class Message {
    private long id;
    private String userName;
    private String content;

    public Message(long id, String userName, String content) {
        this.id = id;
        this.userName = userName;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
