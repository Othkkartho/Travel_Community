package com.learn.travel_community.domain.board;

import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.dto.board.BoardDTO;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

    List<BoardEntity> findTop10ByMemberOrderByCreatedTimeDesc(Member member);

    BoardEntity findAllById(Long boardId);
}