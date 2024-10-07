package com.bit.boardappbackend.service.impl;

import com.bit.boardappbackend.entity.Board;
import com.bit.boardappbackend.entity.ChatRoom;
import com.bit.boardappbackend.repository.BoardRepository;
import com.bit.boardappbackend.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final BoardRepository boardRepository;

    public void createChatRoom(Long auctionId) {
        Board auction = boardRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("경매가 존재하지 않습니다. ID: " + auctionId));

        // 채팅방 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .board(auction)
                .roomName("경매 #" + auctionId + " 채팅방")
                .createdAt(LocalDateTime.now())
                .build();

        // DB에 채팅방 저장
        chatRoomRepository.save(chatRoom);
        System.out.println("채팅방이 생성되었습니다. 경매 ID: " + auctionId);
    }
}
