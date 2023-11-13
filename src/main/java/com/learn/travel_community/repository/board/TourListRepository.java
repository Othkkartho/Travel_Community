package com.learn.travel_community.repository.board;

import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.domain.tour.TourdetailEntity;
import com.learn.travel_community.dto.tour.TourDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourListRepository extends JpaRepository<TourListEntity, Long> {

   List<TourListEntity> findAllByCountryId(Long countryId);

   List<TourdetailEntity> findAllByTourlistId(Long tourlistId);
}
