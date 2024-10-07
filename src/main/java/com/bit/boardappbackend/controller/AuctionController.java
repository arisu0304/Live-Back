package com.bit.boardappbackend.controller;

import com.bit.boardappbackend.dto.AuctionDto;
import com.bit.boardappbackend.service.impl.AuctionService;
import com.bit.boardappbackend.service.impl.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;
    private final StreamingService streamingService;

    @GetMapping("/{id}")
    public ResponseEntity<AuctionDto> getAuctionDetails(@PathVariable Long id) {
        System.out.println(id);
        return ResponseEntity.ok(auctionService.getAuctionRoomDetails(id));
    }

    @PostMapping("/end/{auctionId}")
    public ResponseEntity<?> endAuction(@PathVariable Long auctionId) {
        auctionService.endAuction(auctionId);  // 경매 종료 처리
        streamingService.endStreaming(auctionId);  // 스트리밍 종료 처리
        return ResponseEntity.ok("경매 및 스트리밍이 종료되었습니다.");
    }
}