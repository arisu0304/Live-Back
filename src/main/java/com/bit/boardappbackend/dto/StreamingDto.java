package com.bit.boardappbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreamingDto {

    private Long id;
    private String streamUrl;
    private String streamKey;
    private LocalDateTime streamStartTime;
    private LocalDateTime streamEndTime;
    private String auctionId; // 경매 ID
}