package com.learn.travel_community.repository.board;

import com.learn.travel_community.domain.tour.CountryEntity;
import com.learn.travel_community.domain.tour.TourListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourListRepository extends JpaRepository<TourListEntity, Long> {

   List<TourListEntity> findAllByCountryId(Long countryId);

   TourListEntity findByTourlistId(Long tourlistId);
}
