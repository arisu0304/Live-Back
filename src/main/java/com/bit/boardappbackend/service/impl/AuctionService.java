package com.bit.boardappbackend.service.impl;

import com.bit.boardappbackend.dto.AuctionDto;
import com.bit.boardappbackend.dto.ChatMessageDto;
import com.bit.boardappbackend.entity.Board;
import com.bit.boardappbackend.entity.Streaming;
import com.bit.boardappbackend.livestation.service.LiveStationService;
import com.bit.boardappbackend.repository.BoardRepository;
import com.bit.boardappbackend.repository.StreamingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuctionService {

    private final BoardRepository boardRepository;
    private final ChatRoomService chatRoomService;
    private final LiveStationService liveStationService;
    private final StreamingRepository streamingRepository;

    @Transactional
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void checkAuctionStart() {
        log.info("checkAuctionStart() 실행됨");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future15Minutes = now.plusMinutes(15);

        try {
            // 경매 시작 시간이 15분 이내인 경매들을 찾음
            List<Board> upcomingAuctions = boardRepository.findAuctionsStartingBefore(now, future15Minutes);

            for (Board auction : upcomingAuctions) {
                // 1. 채팅방이 생성되지 않은 경우 채팅방 생성
                if (!auction.isChatRoomCreated()) {
                    chatRoomService.createChatRoom(auction.getId()); // 채팅방 생성
                    auction.setChatRoomCreated(true); // 채팅방 생성 상태 업데이트
                }

                // 2. 스트리밍이 생성되지 않은 경우 스트리밍 채널 생성
                if (!auction.isStreamingCreated()) {
                    String channelId = liveStationService.createChannel(auction.getTitle()); // LiveStation 채널 생성

                    Streaming streaming = Streaming.builder()
                            .streamUrl(channelId) // 실제 스트리밍 URL
                            .streamStartTime(auction.getAuctionStart()) // 경매 시작 시간
                            .streamEndTime(auction.getAuctionStart().plusMinutes(30)) // 스트리밍 종료 시간 설정
                            .board(auction)
                            .streamKey(liveStationService.getChannelInfo(channelId).getStreamKey()) // 스트리밍 키 설정
                            .build();

                    streamingRepository.save(streaming); // 스트리밍 정보 저장
                    auction.setStreamingCreated(true); // 스트리밍 생성 상태 업데이트
                }

                // 3. 경매 상태 업데이트
                boardRepository.save(auction);
            }
        } catch (Exception e) {
            log.error("경매 시작 스케줄링 중 오류 발생: ", e);
        }
    }

    public AuctionDto getAuctionRoomDetails(Long auctionId) {
        // 실제 스트리밍 URL 및 채팅 메시지 불러오기
        Streaming streaming = streamingRepository.findByBoardId(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("스트리밍 정보를 찾을 수 없습니다."));

        List<ChatMessageDto> chatMessages = getChatMessages(auctionId); // 채팅 메시지 불러오기

        return new AuctionDto(streaming.getStreamUrl(), streaming.getStreamKey(), chatMessages);
    }

    private List<ChatMessageDto> getChatMessages(Long auctionId) {
        // 실제로는 DB에서 채팅 메시지를 불러오는 로직이 필요
        return List.of(new ChatMessageDto("User1", "안녕하세요!"), new ChatMessageDto("User2", "환영합니다!"));
    }

    public void endAuction(Long auctionId) {

    }
}
