package com.triplog.chat.model;

public class ChatResponse {
    private String userMessage;  
    private String botReply;    
    private String timestamp;    

    public ChatResponse(String userMessage, String botReply, String timestamp) {
        this.userMessage = userMessage;
        this.botReply = botReply;
        this.timestamp = timestamp;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getBotReply() {
        return botReply;
    }

    public void setBotReply(String botReply) {
        this.botReply = botReply;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}