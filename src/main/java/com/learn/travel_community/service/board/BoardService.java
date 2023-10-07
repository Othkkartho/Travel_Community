package com.learn.travel_community.service.board;

import com.learn.travel_community.dto.board.BoardDTO;
import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }
}
