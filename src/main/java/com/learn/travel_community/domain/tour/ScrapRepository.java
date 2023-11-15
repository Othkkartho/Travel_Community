package com.learn.travel_community.domain.tour;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<ScrapEntity, ScrapId> {
    List<ScrapEntity> findAllByUid(Long uid);
}