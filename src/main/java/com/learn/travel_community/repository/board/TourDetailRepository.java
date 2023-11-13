package com.learn.travel_community.repository.board;

import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.domain.tour.TourdetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourDetailRepository extends JpaRepository<TourdetailEntity, Long> {
    TourdetailEntity findByDetailId(Long detailId);
}
