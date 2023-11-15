package com.learn.travel_community.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByAccessToken(String accessToken);

    Optional<Member> getMemberByNickname(String nickname);
}
