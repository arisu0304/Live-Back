package com.bit.boardappbackend.repository;

import com.bit.boardappbackend.entity.Streaming;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreamingRepository extends JpaRepository<Streaming, Long> {

    Optional<Streaming> findByBoardId(Long boardId);
}
