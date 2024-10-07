package com.bit.boardappbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@SequenceGenerator(
        name = "chatroomSeqGenerator",
        sequenceName = "CHATROOM_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "chatroomSeqGenerator")
    private Long id;

    // 경매 ID와 채팅방을 연결
    @OneToOne
    @JoinColumn(name = "auction_id")
    private Board board;

    // 채팅방의 이름이나 정보
    private String roomName;

    // 채팅방 생성 시간
    private LocalDateTime createdAt;

}
