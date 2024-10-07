package com.bit.boardappbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto {

    private Long id;
    private String roomName;
    private LocalDateTime createdAt;
    private String auctionId; // 경매 ID
}
