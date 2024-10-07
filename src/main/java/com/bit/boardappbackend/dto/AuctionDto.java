package com.bit.boardappbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuctionDto {
    private String streamUrl;
    private String streamKey;
    private List<ChatMessageDto> chatMessages;
}
