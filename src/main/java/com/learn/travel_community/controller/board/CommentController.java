package com.learn.travel_community.controller.board;

import com.learn.travel_community.domain.board.*;
import com.learn.travel_community.dto.board.CommentDTO;
import com.learn.travel_community.service.board.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @PostMapping("/save")
    public String save(@ModelAttribute CommentDTO commentDTO, String boardId) throws IOException {
        commentDTO.setCommentContents(commentDTO.getCommentContents());
        commentDTO.setBoardEntity(boardRepository.findAllById(Long.valueOf(boardId)));
        commentService.save(commentDTO);
        List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardEntity());

        return "redirect:/board/paging";
    }

    @GetMapping("/list")
    public List<CommentDTO> list(BoardEntity boardEntity) {
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntity(boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
            commentDTOList.add(CommentDTO.toCommentDTO(commentEntity));
        }
        return commentDTOList;
    }

    @DeleteMapping("/delete")
    public void delete(Long commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);
        commentRepository.delete(commentEntity);
    }
}