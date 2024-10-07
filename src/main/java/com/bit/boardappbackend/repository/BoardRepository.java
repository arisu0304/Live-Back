package com.bit.boardappbackend.repository;

import com.bit.boardappbackend.entity.Board;
import com.bit.boardappbackend.repository.custom.BoardRepositoryCustom;
import jakarta.websocket.OnError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    Page<Board> searchAll(String searchCondition, String searchKeyword, Pageable pageable);

    // 경매 시작 시간이 15분 이내인 경매들 찾기
    @Query("SELECT b FROM Board b WHERE b.auctionStart BETWEEN :now AND :future")
    List<Board> findAuctionsStartingBefore(LocalDateTime now, LocalDateTime future);
}
