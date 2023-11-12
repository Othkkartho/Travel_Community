package com.learn.travel_community.dto.tour;

import com.learn.travel_community.domain.tour.CountryEntity;
import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.domain.tour.TourdetailEntity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
public class TourListDto {
    private Long tourlistId;

    private String tourName;

    private String tourExp;

    private String tourImage;

    private Long countryId;

    private String countryName;

    public TourListDto(Long tno, String tourName, String tourExp, String tourImage, Long countryId, String countryName) {
        this.tourlistId = tno;
        this.tourName = tourName;
        this.tourExp = tourExp;
        this.tourImage = tourImage;
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public static TourListDto toTourListDto(TourListEntity tourListEntity) {
        TourListDto tourlistDto = new TourListDto();
        tourlistDto.setTourlistId(tourListEntity.getTourlistId());
        tourlistDto.setTourName(tourListEntity.getTourName());
        tourlistDto.setTourExp(tourListEntity.getTourExp());
        tourlistDto.setTourImage(tourListEntity.getTourImage());
        tourlistDto.setCountryId(tourListEntity.getCountryId());
        tourlistDto.setCountryName(tourlistDto.getCountryName());

        return tourlistDto;
    }
}