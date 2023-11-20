package com.learn.travel_community.dto.board;

import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.domain.board.BoardFileEntity;
import com.learn.travel_community.domain.board.BoardRecommendEntity;
import com.learn.travel_community.domain.board.RecommendContentEntity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardRecommendDto {

    private Long id;
    private String theme;
    private int age;
    private int gender;
    private Integer boardHits;
    private List<String> image;
    private List<RecommendContentDto> recommendContents;

    public BoardRecommendDto(Long id, String theme, int age, int gender) {
        this.id = getId();
        this.theme = getTheme();
        this.age = getAge();
        this.gender = getGender();
    }

    public static BoardRecommendDto toBoardRecommentDto(BoardRecommendEntity boardRecommendEntity) {
        BoardRecommendDto boardRecommendDto = new BoardRecommendDto();
        boardRecommendDto.setId(boardRecommendEntity.getId());
        boardRecommendDto.setTheme(boardRecommendEntity.getTheme());
        boardRecommendDto.setAge(boardRecommendEntity.getAge());
        boardRecommendDto.setGender(boardRecommendEntity.getGender());
        boardRecommendDto.setImage(boardRecommendEntity.getRecommendContents().stream().map(RecommendContentEntity::getImage).collect(Collectors.toList()));

        return boardRecommendDto;
    }
}
