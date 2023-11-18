package com.learn.travel_community.service.tour;

import com.learn.travel_community.domain.tour.*;
import com.learn.travel_community.dto.tour.TourDetailDto;
import com.learn.travel_community.dto.tour.TourListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourListService {

    @Autowired
    private TourListRepository tourListRepository;

    @Autowired
    private TourDetailRepository tourDetailRepository;

    @Autowired
    private TopdataRepository topDataRepository;

    public List<TourListDto> search(Long countryId, LocalDate date) {
        // 월별 상위 10개 투어 목록 ID를 조회합니다.
        List<Long> toptourlistIds = topDataRepository.findAllByDate(date).stream()
                .map(TopDataEntity::getTourlistId)
                .collect(Collectors.toList());

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

            // toptourlistIds에 포함된 tourlistId의 TopDataEntities를 모아서 tourListDto의 TopDataEntities에 저장합니다.
            List<TopDataEntity> topDataEntities = toptourlistIds.stream()
                    .filter(id -> id.equals(tourListEntity.getTourlistId()))
                    .map(id -> topDataRepository.findByTourlistId(id))
                    .collect(Collectors.toList());

            tourListDto.setTopDataEntities(topDataEntities);

            tourListDtos.add(tourListDto);
        }
        return tourListDtos;
    }
    public TourDetailDto findAllByDetailId(Long detailId) {
        TourdetailEntity tourdetailEntity = tourDetailRepository.findAllByDetailIdOrderByRankNoAsc(detailId);
        TourDetailDto tourDetailDto = TourDetailDto.toTourdetailDto(tourdetailEntity);

        return tourDetailDto;
    }

    public List<Long> getTopTourlistIdsByDate(LocalDate date) {
        List<TopDataEntity> topDataEntities = topDataRepository.findAllByDate(date);
        List<Long> tourlistIds = new ArrayList<>();
        for (TopDataEntity topDataEntity : topDataEntities) {
            tourlistIds.add(topDataEntity.getTourListEntity().getTourlistId());
        }
        return tourlistIds;
    }
}