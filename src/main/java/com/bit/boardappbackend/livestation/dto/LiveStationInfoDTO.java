package com.bit.boardappbackend.livestation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LiveStationInfoDTO {
    private String channelId;
    private String channelName;
    private String channelStatus;
    private int cdnInstanceNo;
    private String cdnStatus;

    private String publishUrl;
    private String streamKey;

    private Long sellerId; // 판매자 ID 추가
    private String userRole; // 'seller' 또는 'buyer'로 구분

    private int lectureId;
}
