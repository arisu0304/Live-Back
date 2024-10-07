package com.bit.boardappbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "chatMessageSeqGenerator",
        sequenceName = "CHATMESSAGE_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class ChatMessage {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "chatMessageSeqGenerator"
    )
    private Long id;

    private String message; // 메시지 내용

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 메시지를 보낸 사용자

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // 메시지가 속한 채팅방
}