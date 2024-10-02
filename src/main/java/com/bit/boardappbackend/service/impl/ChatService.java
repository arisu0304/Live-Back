package com.bit.boardappbackend.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private final List<String> messageStore = new ArrayList<>();

    public void saveMessage(String message) {
        // 메시지를 저장하는 로직 (예: DB나 Redis 저장소 사용 가능)
        messageStore.add(message);
    }

    public List<String> getMessages() {
        // 저장된 메시지 목록 반환
        return new ArrayList<>(messageStore);
    }
}