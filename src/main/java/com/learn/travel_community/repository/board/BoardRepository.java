package com.learn.travel_community.repository.board;

import com.learn.travel_community.domain.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}