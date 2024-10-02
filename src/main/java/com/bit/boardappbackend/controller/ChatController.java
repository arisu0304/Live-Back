package com.bit.boardappbackend.controller;

import com.bit.boardappbackend.service.impl.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/messages")
    public List<String> getMessages() {
        // ChatService를 통해 메시지 목록 반환 (예시)
        return chatService.getMessages();
    }
}

