package com.bit.boardappbackend.controller;

import com.bit.boardappbackend.dto.ChatMessageDto;
import com.bit.boardappbackend.service.impl.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class ChatController {

    // 클라이언트가 "/app/chat.sendMessage"로 보내는 메시지를 처리
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDto sendMessage(ChatMessageDto message) {
        return message;
    }
}

