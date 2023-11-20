package com.learn.travel_community.domain.board;

import com.learn.travel_community.domain.BaseTimeEntity;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.dto.board.BoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

// DB의 테이블 역할
@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column(columnDefinition = "integer default 0")
    private Integer ageGroup;

    @Column
    private int boardHits;

    @Column
    private int fileAttached; // 1 or 0

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Viewer> views = new ArrayList<>();

    public static BoardEntity toSaveEntity(Member member, BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setMember(member);
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(0); // 파일 없음.
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(Member member, BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setMember(member);
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setFileAttached(0);
        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(Member member, BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setMember(member);
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(1); // 파일 있음.
        return boardEntity;
    }

    public static BoardEntity toUpdateFileEntity(Member member, BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setMember(member);
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setFileAttached(1);
        return boardEntity;
    }
}
