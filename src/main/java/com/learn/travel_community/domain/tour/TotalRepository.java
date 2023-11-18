package com.learn.travel_community.domain.tour;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TotalRepository extends JpaRepository<TotalEntity, Long> {
    List<TotalEntity> findRegionInterestScoreByAgeGroup(Integer ageGroup);
}
