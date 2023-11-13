package com.learn.travel_community.dto.tour;


import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.domain.tour.TourdetailEntity;
import lombok.*;

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

    private TourdetailEntity tourdetailEntity;



    public TourListDto(Long tno, String tourName, String tourExp, String tourImage, Long countryId, String countryName, TourdetailEntity tourdetailEntity) {
        this.tourlistId = tno;
        this.tourName = tourName;
        this.tourExp = tourExp;
        this.tourImage = tourImage;
        this.countryId = countryId;
        this.countryName = countryName;
        this.tourdetailEntity = tourdetailEntity;
    }

    public static TourListDto toTourListDto(TourListEntity tourListEntity) {
        TourListDto tourlistDto = new TourListDto();
        tourlistDto.setTourlistId(tourListEntity.getTourlistId());
        tourlistDto.setTourName(tourListEntity.getTourName());
        tourlistDto.setTourExp(tourListEntity.getTourExp());
        tourlistDto.setTourImage(tourListEntity.getTourImage());
        tourlistDto.setCountryId(tourListEntity.getCountryId());
        tourlistDto.setCountryName(tourlistDto.getCountryName());
        tourlistDto.setTourdetailEntity(tourlistDto.getTourdetailEntity());

        return tourlistDto;
    }
}