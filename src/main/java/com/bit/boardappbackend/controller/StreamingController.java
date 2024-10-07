package com.bit.boardappbackend.controller;

import com.bit.boardappbackend.entity.Streaming;
import com.bit.boardappbackend.service.impl.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/streaming")
public class StreamingController {
    private final StreamingService streamingService;

    @GetMapping("/info/{boardId}")
    public ResponseEntity<?> getStreamingInfo(@PathVariable Long boardId) {
        Streaming streaming = streamingService.getStreamingByBoardId(boardId);
        return ResponseEntity.ok(streaming);
    }

    @PostMapping("/end/{boardId}")
    public ResponseEntity<?> endStreaming(@PathVariable Long boardId) {
        streamingService.endStreaming(boardId);
        return ResponseEntity.ok("스트리밍 종료");
    }
}
