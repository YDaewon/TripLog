package com.triplog.chat.model.service;

import com.triplog.chat.OpenAiClient;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final OpenAiClient openAiClient;

    public ChatServiceImpl(OpenAiClient openAiClient) {
        this.openAiClient = openAiClient;
    }

    @Override
    public String getChatbotReply(String message) {
        return openAiClient.askQuestion(message);
    }
}
