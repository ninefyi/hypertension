package com.openworldtravels.www.hypertension;

/**
 * Created by pitichampeethong on 10/23/2016 AD.
 */

public class ChatMessage {

    private String content;
    private boolean isMine;

    public ChatMessage(String content, boolean isMine) {
        this.content = content;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }
}
