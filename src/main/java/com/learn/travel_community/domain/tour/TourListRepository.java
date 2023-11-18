package com.learn.travel_community.domain.tour;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourListRepository extends JpaRepository<TourListEntity, Long> {
   List<TourListEntity> findAllByCountryId(Long countryId);

   TourListEntity findTourNameByTourlistId(Long tourlistId);

}
