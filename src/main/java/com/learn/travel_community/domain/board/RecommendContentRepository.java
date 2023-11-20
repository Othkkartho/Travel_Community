package com.learn.travel_community.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendContentRepository extends JpaRepository<RecommendContentEntity, Long> {
    List<RecommendContentEntity> findAllByBoardRecommendIdIn(List<Long> boardRecommendId);
}
