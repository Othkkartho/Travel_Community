package com.learn.travel_community.service.tour;

import com.learn.travel_community.domain.tour.TourDetailRepository;
import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.domain.tour.TourListRepository;
import com.learn.travel_community.domain.tour.TourdetailEntity;
import com.learn.travel_community.dto.tour.TourDetailDto;
import com.learn.travel_community.dto.tour.TourListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourListService {

    @Autowired
    private TourListRepository tourListRepository;

    @Autowired
    private TourDetailRepository tourDetailRepository;

    public List<TourListDto> search(Long countryId) {
        // 투어 목록 ID 목록을 생성합니다.
        List<Long> tourlistIds = tourListRepository.findAllByCountryId(countryId).stream()
                .map(TourListEntity::getTourlistId)
                .collect(Collectors.toList());

        // 투어 상세 정보를 조회합니다.
        List<TourdetailEntity> tourdetailEntities = tourDetailRepository.findAllByTourlistIdIn(tourlistIds);

        // 투어 목록 ID별 투어 상세 정보를 맵에 저장합니다.
        Map<Long, List<TourdetailEntity>> tourdetailEntitiesMap = tourdetailEntities.stream()
                .collect(Collectors.groupingBy(TourdetailEntity::getTourlistId));

        // 투어 목록을 생성합니다.
        List<TourListDto> tourListDtos = new ArrayList<>();
        for (TourListEntity tourListEntity : tourListRepository.findAllByCountryId(countryId)) {
            TourListDto tourListDto = TourListDto.toTourListDto(tourListEntity);
            tourListDto.setTourdetailEntities(tourdetailEntitiesMap.get(tourListEntity.getTourlistId()));
            tourListDtos.add(tourListDto);
        }

        return tourListDtos;
    }

    public TourDetailDto findAllByDetailId(Long detailId) {
        TourdetailEntity tourdetailEntity = tourDetailRepository.findAllByDetailId(detailId);
        TourDetailDto tourDetailDto = TourDetailDto.toTourdetailDto(tourdetailEntity);

        return tourDetailDto;
    }
}