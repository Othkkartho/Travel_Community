package com.learn.travel_community.domain.tour;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourDetailRepository extends JpaRepository<TourdetailEntity, Long> {
    TourdetailEntity findByDetailId(Long detailId);
    List<TourdetailEntity> findAllByTourlistIdIn(List<Long> tourlistIds);

}
