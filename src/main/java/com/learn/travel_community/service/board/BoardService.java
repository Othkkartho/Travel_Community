package com.learn.travel_community.service.board;

import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.domain.board.BoardFileEntity;
import com.learn.travel_community.domain.board.BoardFileRepository;
import com.learn.travel_community.domain.board.BoardRepository;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.board.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final MemberRepository memberRepository;
    @Value("${file.path}")
    private String uploadFolder;

    public void save(Member member, BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부 파일 없음.
            BoardEntity boardEntity = BoardEntity.toSaveEntity(member, boardDTO);
            boardRepository.save(boardEntity);
        } else {
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(member, boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(savedId).get();
            // 첨부 파일 있음.
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFilename = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                String savePath = System.getProperty("user.dir") + uploadFolder + "board/" + storedFileName; // 4. 저장 위치에 파일 이름 붙이기
                boardFile.transferTo(new File(savePath));

                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
        }
    }

    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            return BoardDTO.toBoardDTO(boardEntity);
        } else {
            return null;
        }
    }

    public BoardDTO update(Member member, BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(member, boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    @Transactional
    public void delete(Long uid, Long id) throws Exception {
        Member member = memberRepository.findById(uid).orElseThrow();

        // 게시글에 포함된 이미지 삭제
        List<BoardFileEntity> boardFileEntities = boardFileRepository.findAllByBoardId(id);
        for (BoardFileEntity boardFileEntity : boardFileEntities) {
            String storedFileName = boardFileEntity.getStoredFileName();
            if (storedFileName != null) {
                Path imagePath = Paths.get(storedFileName);
                if (Files.exists(imagePath)) {
                    Files.delete(imagePath);
                }
            }
        }

        member.getBoards().removeIf(board -> board.getId().equals(id));
        boardRepository.deleteById(id);
    }
    @Transactional
    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 5; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 10개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        // 목록: id, member, title, hits, createdTime
        return boardEntities.map(BoardDTO::toBoardDTO);
    }
}