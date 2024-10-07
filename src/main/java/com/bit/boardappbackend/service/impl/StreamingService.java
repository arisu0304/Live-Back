package com.bit.boardappbackend.service.impl;

import com.bit.boardappbackend.entity.Board;
import com.bit.boardappbackend.entity.Streaming;
import com.bit.boardappbackend.repository.BoardRepository;
import com.bit.boardappbackend.repository.StreamingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamingService {
    private final StreamingRepository streamingRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Streaming createStreaming(Long boardId, String streamUrl, String streamKey) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("경매 게시판을 찾을 수 없습니다."));

        Streaming streaming = Streaming.builder()
                .streamUrl(streamUrl)
                .streamKey(streamKey)
                .streamStartTime(LocalDateTime.now())
                .board(board)
                .build();

        return streamingRepository.save(streaming);
    }

    public Streaming getStreamingByBoardId(Long boardId) {
        return streamingRepository.findByBoardId(boardId)
                .orElseThrow(() -> new IllegalArgumentException("스트리밍 정보를 찾을 수 없습니다."));
    }

    public void endStreaming(Long boardId) {
        Streaming streaming = streamingRepository.findByBoardId(boardId)
                .orElseThrow(() -> new IllegalArgumentException("스트리밍 정보를 찾을 수 없습니다."));

        streaming.setStreamEndTime(LocalDateTime.now());
        streamingRepository.save(streaming);
    }
}
