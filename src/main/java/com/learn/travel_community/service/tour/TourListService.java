package com.learn.travel_community.service.tour;

import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.domain.tour.TourdetailEntity;
import com.learn.travel_community.dto.tour.TourDetailDto;
import com.learn.travel_community.dto.tour.TourListDto;
import com.learn.travel_community.repository.board.TourDetailRepository;
import com.learn.travel_community.repository.board.TourListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourListService {

    @Autowired
    private TourListRepository tourListRepository;
    private TourDetailRepository tourDetailRepository;

    @Transactional
    public List<TourListDto> findAllByCountryId(Long countryId) {
        List<TourListEntity> tourListEntities = tourListRepository.findAllByCountryId(countryId);
        List<TourListDto> tourListDtos = new ArrayList<>();
        for (TourListEntity tourListEntity : tourListEntities) {
            TourListDto tourListDto = TourListDto.toTourListDto(tourListEntity);
            tourListDtos.add(tourListDto);
        }

        return tourListDtos;
    }

    public List<TourDetailDto> findAllByTourlistId(Long tourlistId) {
        List<TourdetailEntity> tourdetailEntities = tourListRepository.findAllByTourlistId(tourlistId);
        List<TourDetailDto> tourDetailDtos = new ArrayList<>();
        for (TourdetailEntity tourdetailEntity : tourdetailEntities) {
            TourDetailDto tourDetailDto = TourDetailDto.toTuple(tourdetailEntity);
            tourDetailDtos.add(tourDetailDto);
        }

        return tourDetailDtos;
    }

    public TourDetailDto findByDetailId(Long detailId) {
        TourdetailEntity tourdetailEntity = tourDetailRepository.findByDetailId(detailId);
        TourDetailDto tourDetailDto = TourDetailDto.toTourdetailDto(tourdetailEntity);

        return tourDetailDto;
    }
}