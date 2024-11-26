package com.triplog.chat.model;

public class ChatResponse {
    private String userMessage;   // 사용자 메시지
    private String botReply;      // 챗봇의 응답
    private String timestamp;     // 응답 시간

    // 생성자
    public ChatResponse(String userMessage, String botReply, String timestamp) {
        this.userMessage = userMessage;
        this.botReply = botReply;
        this.timestamp = timestamp;
    }

    // Getter와 Setter
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