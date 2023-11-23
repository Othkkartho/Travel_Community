package com.learn.travel_community.dto.board;

import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.domain.board.BoardFileEntity;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.tour.TagEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    private Long id;
    private Member member;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private Integer ageGroup;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
    private List<BoardFileEntity> boardFileEntityList;
    private TagEntity tagEntity;
    private String tagName;

    private Long tourlistId;

    private List<MultipartFile> boardFile; // save.html -> Controller 파일 담는 용도
    private List<String> originalFileName; // 원본 파일 이름
    private List<String> storedFileName; // 서버 저장용 파일 이름
    private int fileAttached = 0; // 파일 첨부 여부(첨부 1, 미첨부 0)

    public BoardDTO(Long id, Member member, String boardTitle, String boardContents, int boardHits, Integer ageGroup, LocalDateTime boardCreatedTime,
                    LocalDateTime boardUpdatedTime, List<BoardFileEntity> boardFileEntityList, String tagName, Long tourlistId) {
        this.id = id;
        this.member = member;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.ageGroup = ageGroup;
        this.boardCreatedTime = boardCreatedTime;
        this.boardUpdatedTime = boardUpdatedTime;
        this.boardFileEntityList = boardFileEntityList;
        this.tagName = tagName;
        this.tourlistId = tourlistId;
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setMember(boardEntity.getMember());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        boardDTO.setTagName(boardDTO.getTagName());
        if (boardEntity.getTagEntityList() != null && !boardEntity.getTagEntityList().isEmpty()) {
            boardDTO.setTagEntity(boardEntity.getTagEntityList().get(0)); // get the first tag
        } else {
            boardDTO.setTagEntity(null);
        }
        if (boardEntity.getFileAttached() == 0) {
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0
        } else {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 1
            // 파일 이름을 가져가야 함.
            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);
        }

        return boardDTO;
    }
}