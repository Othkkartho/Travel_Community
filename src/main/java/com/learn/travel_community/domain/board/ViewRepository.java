package com.learn.travel_community.domain.board;

import com.learn.travel_community.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewRepository extends JpaRepository<Viewer, Long> {
    Viewer findByMemberAndBoard(Member member, BoardEntity board);
}
