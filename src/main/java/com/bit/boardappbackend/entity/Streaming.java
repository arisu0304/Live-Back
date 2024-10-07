package com.bit.boardappbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "streamingSeqGenerator",
        sequenceName = "STREAMING_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Streaming {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "streamingSeqGenerator"
    )
    private Long id;

    private String streamUrl; // 스트리밍 URL (Ncloud LiveStation에서 제공)
    private String streamKey; // 스트리밍 키 (OBS에서 사용)

    private LocalDateTime streamStartTime; // 스트리밍 시작 시간
    private LocalDateTime streamEndTime; // 스트리밍 종료 시간

    @OneToOne
    @JoinColumn(name = "auction_id")
    private Board board; // 경매와 스트리밍을 1:1 연결
}

