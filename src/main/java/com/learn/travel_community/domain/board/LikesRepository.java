package com.learn.travel_community.domain.board;

import com.learn.travel_community.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByMemberAndBoard(Member member, BoardEntity board);

    @Modifying
    @Query(value = "select count(*) from Likes l where l.board=:bid")
    void getBoardLikesCount(@Param("bid") Long bid);

    @Modifying
    @Query(value = "select count(*) from Likes l where l.member=:uid")
    void getMemberLikesCount(@Param("uid") Long uid);
}
