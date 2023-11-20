package com.learn.travel_community.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRecommendRepository extends JpaRepository<BoardRecommendEntity, Long> {
    List<BoardRecommendEntity> findAllByAgeAndGender(int age, int gender);
}