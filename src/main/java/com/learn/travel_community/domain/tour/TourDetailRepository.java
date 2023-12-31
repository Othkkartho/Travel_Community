package com.learn.travel_community.domain.tour;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourDetailRepository extends JpaRepository<TourdetailEntity, Long> {
    TourdetailEntity findAllByDetailIdOrderByRankNoAsc(Long detailId);
    TourdetailEntity findAllByDetailId(Long detailId);
    List<TourdetailEntity> findAllByTourlistIdIn(List<Long> tourlistIds);
}
