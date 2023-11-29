package com.learn.travel_community.domain.board;

import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.dto.board.CommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findTop10ByMemberOrderByCreatedTimeDesc(Member member);

    List<CommentEntity> findAllByBoardEntity(BoardEntity boardEntity);

    @Transactional
    @Modifying
    @Query(value = "delete from CommentEntity c where c.id=:commentId")
    void deleteById(Long commentId);
}