package com.learn.travel_community.repository.board;

import com.learn.travel_community.domain.board.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
}
