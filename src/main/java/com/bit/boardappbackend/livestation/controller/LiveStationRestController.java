package com.bit.boardappbackend.livestation.controller;

import com.bit.boardappbackend.dto.ResponseDto;
import com.bit.boardappbackend.entity.CustomUserDetails;
import com.bit.boardappbackend.livestation.dto.LiveStationInfoDTO;
import com.bit.boardappbackend.livestation.dto.LiveStationUrlDTO;
import com.bit.boardappbackend.livestation.service.LiveStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/live")
@RequiredArgsConstructor
@RestController
@Slf4j
public class LiveStationRestController {
    private final LiveStationService liveStationService;

//    public static String channelId;

    //채널 아이디가 있지만 강의중 것을 확인할 때
    @GetMapping("/info/{channelId}")
    public ResponseEntity<?> getChannelInfo(@PathVariable String channelId) {
        ResponseDto<LiveStationInfoDTO> responseDTO = new ResponseDto<>();

        LiveStationInfoDTO dto = liveStationService.getChannelInfo(channelId);

        responseDTO.setItem(dto);
        responseDTO.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/url/{channelId}")
    public ResponseEntity<?> getServiceURL(@PathVariable String channelId) {
        ResponseDto<LiveStationUrlDTO> responseDTO = new ResponseDto<>();

        List<LiveStationUrlDTO> dtoList = liveStationService.getServiceURL(channelId, "GENERAL");

        responseDTO.setItems(dtoList);
        responseDTO.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDTO);
    }

    //강사가 강의 개설
    @PostMapping("/lecture")
    public ResponseEntity<?> createLecture(
                                           @RequestParam String title) {
        try {
            log.info(title);
            ResponseDto<LiveStationInfoDTO> responseDTO = new ResponseDto<>();


            String channelId = liveStationService.createChannel(title);


            LiveStationInfoDTO liveStationInfoDTO = liveStationService.getChannelInfo(channelId);

            responseDTO.setItem(liveStationInfoDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/student/lecture")
    public ResponseEntity<?> getLecture(@RequestParam String channelId) {
        log.info("channelId============{}", channelId);
        ResponseDto<LiveStationUrlDTO> response = new ResponseDto<>();

            LiveStationInfoDTO dto = liveStationService.getChannelInfo(channelId);


            List<LiveStationUrlDTO> urlList = liveStationService.getServiceURL(channelId, "GENERAL");
            LiveStationUrlDTO lectureId = LiveStationUrlDTO.builder()
                    .build();

            response.setItem(lectureId);
            response.setItems(urlList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);

    }

    @DeleteMapping("/delete/{channelId}")
    public ResponseEntity<?> deleteChannel(@PathVariable String channelId) {
        return liveStationService.deleteChannel(channelId);
    }

    @GetMapping("/auction/{liveStationId}")
    public ResponseEntity<?> getLiveStation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @PathVariable String liveStationId) {
        ResponseDto<LiveStationInfoDTO> response = new ResponseDto<>();

        // 현재 접속한 사용자 정보 가져오기
        String currentUsername = customUserDetails.getUsername();

        // 라이브 스테이션 정보 가져오기
        LiveStationInfoDTO liveStationInfoDTO = liveStationService.getChannelInfo(liveStationId);

        // 해당 경매 물품의 작성자 정보와 현재 로그인한 사용자 비교
        if (liveStationInfoDTO.getSellerId().equals(currentUsername)) {
            // 판매자라면 OBS 스트리밍을 위한 설정 제공
            liveStationInfoDTO.setUserRole("seller");
        } else {
            // 구매자라면 스트리밍 시청과 채팅방 입장 제공
            liveStationInfoDTO.setUserRole("buyer");
        }

        response.setItem(liveStationInfoDTO);
        response.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok().body(response);
    }
}
