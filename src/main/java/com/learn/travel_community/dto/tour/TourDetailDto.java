package com.learn.travel_community.dto.tour;

import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.domain.tour.TourdetailEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
public class TourDetailDto {
    private Long detailId;
    private String detailName;
    private String detailExp;
    private String detailImg;
    private String detailImgUrl;

    private TourListEntity tourListEntity;

    public TourDetailDto(Long detailId, String detailName, String detailExp, String detailImg, String detailImgUrl, TourListEntity tourListEntity) {
        this.detailId = detailId;
        this.detailName = detailName;
        this.detailExp = detailExp;
        this.detailImg = detailImg;
        this.detailImgUrl = detailImgUrl;
        this.tourListEntity = tourListEntity;
    }

    public static TourDetailDto toTourdetailDto(TourdetailEntity tourdetailEntity) {
        TourDetailDto tourdetailDto = new TourDetailDto();
        tourdetailDto.setDetailId(tourdetailEntity.getDetailId());
        tourdetailDto.setDetailName(tourdetailEntity.getDetailName());
        tourdetailDto.setDetailExp(tourdetailEntity.getDetailExp());
        tourdetailDto.setDetailImg(tourdetailEntity.getDetailImg());
        tourdetailDto.setDetailImgUrl(tourdetailEntity.getDetailImgUrl());
        tourdetailDto.setTourListEntity(tourdetailEntity.getTourListEntity());

        return tourdetailDto;
    }

    public String getDetailImgUrl() {
        return String.format("http://localhost:8080/resources/static/tour/%s", this.detailImg);
    }

}