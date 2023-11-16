package com.learn.travel_community.domain.board;

import com.learn.travel_community.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findTop10ByMemberOrderByCreatedTimeDesc(Member member);

    List<CommentEntity> findAllByBoardEntity(BoardEntity boardEntity);
}