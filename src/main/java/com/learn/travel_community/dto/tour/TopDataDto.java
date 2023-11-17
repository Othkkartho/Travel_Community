package com.learn.travel_community.dto.tour;

import com.learn.travel_community.domain.tour.TopDataEntity;
import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.domain.tour.TourdetailEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
public class TopDataDto {
    private Long id;
    private Integer rankNo;

    private Integer traffic;

    private Date date;

    private Long tourlistId;

    public TopDataDto(Long id, Integer rankNo, Integer traffic, Date date, Long tourlistId) {
        this.id = id;
        this.rankNo = rankNo;
        this.traffic = traffic;
        this.date = date;
        this.tourlistId = tourlistId;
    }

    public static TopDataDto toTopdataDto(TopDataEntity topDataEntity) {
        TopDataDto topDataDto = new TopDataDto();
        topDataDto.setId(topDataEntity.getId());
        topDataDto.setRankNo(topDataEntity.getRankNo());
        topDataDto.setTraffic(topDataEntity.getTraffic());
        topDataDto.setDate(topDataEntity.getDate());
        topDataDto.setTourlistId(topDataEntity.getTourlistId());

        return topDataDto;
    }
}