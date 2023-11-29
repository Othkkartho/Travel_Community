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
    private final CommentService commentService;

    @PostMapping("/save")
    public String save(@ModelAttribute CommentEntity commentEntity, String boardId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setBoardEntity(boardRepository.findAllById(Long.valueOf(boardId)));
        commentService.save(commentDTO);

        return "redirect:/board/paging";
    }

    @DeleteMapping("/delete")
    public String delete(Long boardId, Long commentId) {
        commentService.delete(commentId);

        return "redirect:/board/" + boardId;
    }
}