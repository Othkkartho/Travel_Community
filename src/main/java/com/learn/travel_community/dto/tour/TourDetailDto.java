package com.learn.travel_community.dto.tour;

import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.domain.tour.TourdetailEntity;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
public class TourDetailDto {
    private Long detailId;
    private String detailName;
    private String detailExp;
    private String detailImg;

    private Long tourlistId;

    public TourDetailDto(Long detailId, String detailName, String detailExp, String detailImg, Long tourlistId) {
        this.detailId = detailId;
        this.detailName = detailName;
        this.detailExp = detailExp;
        this.detailImg = detailImg;
        this.tourlistId = tourlistId;
    }

    public static TourDetailDto toTourdetailDto(TourdetailEntity tourdetailEntity) {
        TourDetailDto tourdetailDto = new TourDetailDto();
        tourdetailDto.setDetailId(tourdetailEntity.getDetailId());
        tourdetailDto.setDetailName(tourdetailEntity.getDetailName());
        tourdetailDto.setDetailExp(tourdetailEntity.getDetailExp());
        tourdetailDto.setDetailImg(tourdetailEntity.getDetailImg());
        tourdetailDto.setTourlistId(tourdetailDto.getTourlistId());

        return tourdetailDto;
    }

    public static TourDetailDto toTuple(TourdetailEntity tourdetailEntity) {
        return new TourDetailDto(
                tourdetailEntity.getDetailId(),
                tourdetailEntity.getDetailName(),
                tourdetailEntity.getDetailExp(),
                tourdetailEntity.getDetailImg(),
                tourdetailEntity.getTourListEntity().getTourlistId()
        );
    }
}