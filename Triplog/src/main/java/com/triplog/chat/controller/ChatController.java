package com.triplog.chat.controller;

import java.text.SimpleDateFormat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triplog.chat.model.ChatRequest;
import com.triplog.chat.model.ChatResponse;
import com.triplog.chat.model.service.ChatService;

import java.util.Date;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:5173") // 프론트엔드 주소
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        if (request.getMessage() == null || request.getMessage().isEmpty()) {
            return ResponseEntity.badRequest().body(new ChatResponse("No message", "Message is missing or empty", getCurrentTimestamp()));
        }

        // 챗봇 응답 받기
        String botReply = chatService.getChatbotReply(request.getMessage());
        
        // 사용자 메시지와 챗봇의 응답을 포함한 출력 형식 지정
        ChatResponse response = new ChatResponse(request.getMessage(), botReply, getCurrentTimestamp());
        
        return ResponseEntity.ok(response);
    }

    // 현재 시간 가져오기
    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}