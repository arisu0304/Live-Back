package com.bit.boardappbackend.repository;

import com.bit.boardappbackend.entity.Board;
import com.bit.boardappbackend.repository.custom.BoardRepositoryCustom;
import jakarta.websocket.OnError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    Page<Board> searchAll(String searchCondition, String searchKeyword, Pageable pageable);

}
