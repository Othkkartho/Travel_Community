package com.learn.travel_community.domain.tour;

import com.learn.travel_community.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<ScrapEntity, Long> {
    List<ScrapEntity> findAllByUid(Long uid);

    List<ScrapEntity> findAllByMember(Member member);
}