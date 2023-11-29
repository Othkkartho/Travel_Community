package com.learn.travel_community.domain.tour;

import com.learn.travel_community.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScrapRepository extends JpaRepository<ScrapEntity, Long> {
    List<ScrapEntity> findAllByUid(Long uid);

    List<ScrapEntity> findAllByMember(Member member);

    ScrapEntity findByMemberAndDetailEntity(Member member, TourdetailEntity tourdetailEntity);

    @Transactional
    @Modifying
    @Query(value = "delete from ScrapEntity s where s.sid=:scrapId")
    void deleteBySid(@Param("scrapId") Long scrapId);
}