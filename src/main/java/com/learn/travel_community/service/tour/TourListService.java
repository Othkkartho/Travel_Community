package com.learn.travel_community.service.tour;

import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.dto.tour.TourListDto;
import com.learn.travel_community.repository.board.TourListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TourListService {

    private final TourListRepository tourListRepository;

    public TourListService(TourListRepository tourListRepository) {
        this.tourListRepository = tourListRepository;
    }

    public List<TourListDto> findAllByCountryId(Long countryId) {
        List<TourListEntity> tourListEntities = tourListRepository.findAllByCountryId(countryId);
        List<TourListDto> tourListDtos = new ArrayList<>();
        for (TourListEntity tourListEntity : tourListEntities) {
            TourListDto tourListDto = TourListDto.toTourListDto(tourListEntity);
            tourListDtos.add(tourListDto);
        }

        return tourListDtos;
    }

    public TourListDto findByTourlistId(Long tourlistId) {
        TourListEntity tourListEntity = tourListRepository.findByTourlistId(tourlistId);
        TourListDto tourListDto = TourListDto.toTourListDto(tourListEntity);

        return tourListDto;
    }
}