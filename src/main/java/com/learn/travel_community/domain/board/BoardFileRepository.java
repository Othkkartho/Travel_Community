package com.learn.travel_community.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
    List<BoardFileEntity> findAllByBoardId(Long boardId);
}
