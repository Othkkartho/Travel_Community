package com.learn.travel_community.domain.tour;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface TopdataRepository extends JpaRepository<TopDataEntity, Long> {
    List<TopDataEntity> findAllByDate(LocalDate date);

    TopDataEntity findByTourlistId(Long tourlistId);
}