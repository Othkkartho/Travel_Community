package com.learn.travel_community.service.board;

import com.learn.travel_community.domain.board.*;
import com.learn.travel_community.dto.board.BoardRecommendDto;
import com.learn.travel_community.dto.board.RecommendContentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class boardRecommendService {

    @Autowired
    private final BoardRecommendRepository boardRecommendRepository;
    @Autowired
    private final RecommendContentRepository recommendContentRepository;

    public List<BoardRecommendDto> findAllByAgeAndGender(int age, int gender) {
        List<BoardRecommendEntity> boardRecommendEntities = boardRecommendRepository.findAllByAgeAndGender(age, gender);

        List<Long> boardRecommendIds = boardRecommendEntities.stream()
                .map(BoardRecommendEntity::getId)
                .collect(Collectors.toList());

        List<RecommendContentEntity> recommendContentEntities = recommendContentRepository.findAllByBoardRecommendIdIn(boardRecommendIds);

        List<BoardRecommendDto> boardRecommendDtos = new ArrayList<>();
        for (BoardRecommendEntity boardRecommendEntity : boardRecommendEntities) {
            BoardRecommendDto boardRecommendDto = BoardRecommendDto.toBoardRecommentDto(boardRecommendEntity);

            List<RecommendContentDto> recommendContentDtosForBoard = recommendContentEntities.stream()
                    .filter(recommendContentEntity -> recommendContentEntity.getBoardRecommendEntity().getId().equals(boardRecommendEntity.getId()))
                    .map(RecommendContentDto::toRecommendContentDto)
                    .collect(Collectors.toList());

            boardRecommendDto.setRecommendContents(recommendContentDtosForBoard);
            boardRecommendDtos.add(boardRecommendDto);
        }

        return boardRecommendDtos;
    }

    public BoardRecommendDto findOne(Long id) {
        BoardRecommendEntity boardRecommendEntity = boardRecommendRepository.findById(id).orElse(null);
        return boardRecommendEntity == null ? null : BoardRecommendDto.toBoardRecommentDto(boardRecommendEntity);
    }
}
