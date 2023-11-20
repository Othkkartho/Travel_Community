package com.learn.travel_community.dto.board;

import com.learn.travel_community.domain.board.BoardRecommendEntity;
import com.learn.travel_community.domain.board.RecommendContentEntity;
import com.learn.travel_community.domain.board.RecommendContentRepository;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
public class RecommendContentDto {
    private Long id;
    private String title;
    private String introduce;
    private String recommendContents;
    private String image;

    public RecommendContentDto(Long id, String title, String introduce, String recommendContents, String image) {
        this.id = getId();
        this.title = getTitle();
        this.introduce = getIntroduce();
        this.recommendContents = getRecommendContents();
        this.image = getImage();
    }

    public static RecommendContentDto toRecommendContentDto(RecommendContentEntity recommendContentEntity) {
        RecommendContentDto recommendContentDto = new RecommendContentDto();
        recommendContentDto.setId(recommendContentEntity.getId());
        recommendContentDto.setTitle(recommendContentEntity.getTitle());
        recommendContentDto.setIntroduce(recommendContentEntity.getIntroduce());
        recommendContentDto.setRecommendContents(recommendContentEntity.getRecommendContents());
        recommendContentDto.setImage(recommendContentEntity.getImage());

        return recommendContentDto;
    }
}