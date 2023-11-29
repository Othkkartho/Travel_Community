package com.learn.travel_community.service.board;


import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.domain.board.CommentEntity;
import com.learn.travel_community.domain.board.CommentRepository;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.board.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
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

    public List<CommentEntity> findAll(BoardEntity boardEntity) {
        return commentRepository.findAllByBoardEntity(boardEntity);
    }

    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}