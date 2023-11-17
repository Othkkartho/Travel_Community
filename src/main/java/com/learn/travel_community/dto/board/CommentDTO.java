package com.learn.travel_community.dto.board;

import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.domain.board.CommentEntity;
import com.learn.travel_community.domain.member.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
public class CommentDTO {
    private Long id;
    private Member member;
    private String commentWriter;
    private String commentContents;
    private BoardEntity boardEntity;
    private LocalDateTime commentCreatedTime;

    public CommentDTO(Long id, String commentWriter, String commentContents, LocalDateTime commentCreatedTime, BoardEntity boardEntity, Member member) {
        this.id = id;
        this.commentWriter = commentWriter;
        this.commentContents = commentContents;
        this.commentCreatedTime = commentCreatedTime;
        this.boardEntity = boardEntity;
        this.member = member;
    }

    public static CommentDTO toCommentDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getMember().getNickname());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCommentCreatedTime());
        commentDTO.setBoardEntity(commentEntity.getBoardEntity());
        commentDTO.setMember(commentEntity.getMember());
        return commentDTO;
    }
}