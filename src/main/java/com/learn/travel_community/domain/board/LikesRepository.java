package com.learn.travel_community.domain.board;

import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.dto.board.LikeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByMemberAndBoard(Member member, BoardEntity board);

    List<Likes> findAllByMemberOrderByCreatedTimeDesc(Member member);

    @Query(value = "select new com.learn.travel_community.dto.board.LikeDto(l.board.id, count(*)) from Likes l group by l.board")
    List<LikeDto> getBoardsLikesCount();

    @Query(value = "select l from Likes l where l.board=:bid")
    List<Likes> getBoardLikesCount(@Param("bid") Long bid);

    @Query(value = "select count(*) from Likes l where l.member.uid=:uid")
    Long getMemberLikesCount(@Param("uid") Long uid);
}
