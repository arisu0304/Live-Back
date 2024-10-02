package com.bit.boardappbackend.handler;

import com.bit.boardappbackend.service.impl.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class ChatHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final ChatService chatService;
    private final RedisTemplate<String, String> redisTemplate;

    // 욕설 리스트
    private final List<String> badWords = List.of("fuck", "bitch", "1");

    // Redis key prefix for user message count
    private static final String MESSAGE_COUNT_KEY = "messageCount:";

    public ChatHandler(ChatService chatService, RedisTemplate<String, String> redisTemplate) {
        this.chatService = chatService;
        this.redisTemplate = redisTemplate;
    }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {

            session.sendMessage(new TextMessage("채팅방에 입장하셨습니다."));
            sessions.add(session);
        }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> messageData = objectMapper.readValue(payload, Map.class);

        String type = messageData.get("type");
        String username = messageData.get("username");

        if ("join".equals(type)) {
            // 입장 메시지 처리
            String welcomeMessage = username + "님이 입장하셨습니다.";
            session.getAttributes().put("username", username);
            broadcastMessage(welcomeMessage);
        } else if ("message".equals(type)) {
            // 도배 방지
            String sessionId = session.getId();
            if (isSpamming(sessionId)) {
                session.sendMessage(new TextMessage("You are sending messages too quickly. Please wait."));
                return;
            }

            // 욕설 필터링
            String content = filterBadWords(messageData.get("content"));
            String formattedMessage = username + ": " + content;

            // 메시지 저장
            chatService.saveMessage(formattedMessage);

            // 모든 클라이언트에게 메시지 전송
            broadcastMessage(formattedMessage);
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            // 퇴장 메시지 처리
            String goodbyeMessage = username + "님이 퇴장하셨습니다.";
            // 퇴장한 사용자의 세션을 제외한 나머지 세션에만 메시지를 전송
            for (WebSocketSession webSocketSession : sessions) {
                if (webSocketSession.isOpen() && !webSocketSession.equals(session)) {
                    webSocketSession.sendMessage(new TextMessage(goodbyeMessage));
                }
            }
        }
        sessions.remove(session);
    }


    // 모든 클라이언트에게 메시지를 전송하는 메소드
    private void broadcastMessage(String message) throws Exception {
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(message));
            }
        }
    }

    // 욕설 필터링 기능
    private String filterBadWords(String message) {
        String filteredMessage = message;
        for (String badWord : badWords) {
            if (filteredMessage.contains(badWord)) {
                filteredMessage = filteredMessage.replace(badWord, "***"); // 욕설을 ***로 대체
            }
        }
        return filteredMessage;
    }

    // 도배 방지 기능
    private boolean isSpamming(String sessionId) {
        String redisKey = MESSAGE_COUNT_KEY + sessionId;

        // Redis에서 사용자의 메시지 카운트를 가져옴
        String messageCountStr = redisTemplate.opsForValue().get(redisKey);
        int messageCount = (messageCountStr == null) ? 0 : Integer.parseInt(messageCountStr);

        if (messageCount >= 5) {
            // 메시지를 너무 많이 보냈다면 도배로 간주
            return true;
        }

        // 메시지 카운트를 증가시키고, 10초 동안 유지되도록 설정
        redisTemplate.opsForValue().set(redisKey, String.valueOf(messageCount + 1), 10, TimeUnit.SECONDS);
        return false;
    }

}
