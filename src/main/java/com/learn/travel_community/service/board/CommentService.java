package com.learn.travel_community.service.board;


import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.domain.board.BoardRepository;
import com.learn.travel_community.domain.board.CommentEntity;
import com.learn.travel_community.domain.board.CommentRepository;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.board.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    public void save(CommentDTO commentDTO) {
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        commentDTO.setMember(member);
        commentDTO.setCommentWriter(member.getNickname());
        commentDTO.setBoardEntity(commentDTO.getBoardEntity());
        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO);
        commentRepository.save(commentEntity);
    }

    public List<CommentDTO> findAll(BoardEntity boardEntity) {
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntity(boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
            commentDTOList.add(CommentDTO.toCommentDTO(commentEntity));
        }
        return commentDTOList;
    }

    public void delete(Long commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);
        commentRepository.delete(commentEntity);
    }
}