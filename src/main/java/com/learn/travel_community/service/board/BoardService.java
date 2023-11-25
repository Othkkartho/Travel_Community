package com.learn.travel_community.service.board;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.domain.board.BoardFileEntity;
import com.learn.travel_community.domain.board.BoardFileRepository;
import com.learn.travel_community.domain.board.BoardRepository;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.domain.tour.TagEntity;
import com.learn.travel_community.domain.tour.TagRepository;
import com.learn.travel_community.domain.tour.TourListRepository;
import com.learn.travel_community.dto.board.BoardDTO;
import static com.learn.travel_community.dto.board.BoardDTO.toBoardDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
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
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final TourListRepository tourListRepository;
    private final HttpSession httpSession;
    @Value("${file.path}")
    private String uploadFolder;

    public void save(Member member, BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부 파일 없음.
            BoardEntity boardEntity = BoardEntity.toSaveEntity(member, boardDTO);
            boardRepository.save(boardEntity);


            addTag(boardEntity.getId(), boardDTO.getTagName());
        } else {
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(member, boardDTO);
            boardEntity.setFileAttached(1);
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

                addTag(boardEntity.getId(), boardDTO.getTagName());
        }
    }

    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(toBoardDTO(boardEntity));
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
            return toBoardDTO(boardEntity);
        } else {
            return null;
        }
    }

    public void update(Member member, BoardDTO boardDTO) throws IOException {
        Member currentMember = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);

        if (boardDTO.getBoardFile().isEmpty()) {
            BoardEntity boardEntity = BoardEntity.toSaveEntity(member, boardDTO);
            boardRepository.save(boardEntity);
        } else {
            BoardEntity boardEntity = BoardEntity.toUpdateFileEntity(member, boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(savedId).get();

            // 기존 이미지 삭제
            if (boardDTO.getBoardFile().size() > 0) {
                deleteExistingImages(board);
            }

            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFilename = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "-" + originalFilename;
                String savePath = System.getProperty("user.dir") + uploadFolder + "board/" + storedFileName; // 4. 저장 위치에 파일 이름 붙이기
                boardFile.transferTo(new File(savePath));

                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
            if (boardDTO.getBoardFile().size() > 0) {
                boardEntity.setFileAttached(1);
            }
        }
        BoardEntity boardEntity = boardRepository.findById(boardDTO.getId()).get();
        boardEntity.setMember(currentMember);
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());

        boardRepository.save(boardEntity);
    }

    @Transactional
    public void delete(Long uid, Long id) throws Exception {
        Member member = memberRepository.findById(uid).orElseThrow();

        boardRepository.findById(id).ifPresent(boardEntity -> {
            // 게시글에 포함된 이미지 삭제
            deleteExistingImages(boardEntity);

            member.getBoards().removeIf(board -> board.getId().equals(id));
            boardRepository.deleteById(id);
        });
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

    public void deleteExistingImages(BoardEntity boardEntity) {
        List<BoardFileEntity> boardFileEntities = boardFileRepository.findAllByBoardId(boardEntity.getId());
        for (BoardFileEntity boardFileEntity : boardFileEntities) {
            String storedFileName = boardFileEntity.getStoredFileName();
            if (storedFileName != null) {
                Path imagePath = Paths.get(System.getProperty("user.dir") + uploadFolder + "board/" + storedFileName);
                try {
                    Files.delete(imagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<BoardDTO> findRecommendList(Integer ageGroup, int gender) {
        return boardRepository.findAllByAgeGroupAndGender(ageGroup, gender).stream()
                .map(boardEntity -> toBoardDTO(boardEntity))
                .collect(Collectors.toList());
    }

    private void addTag(Long boardId, String tagName) {
        Long tourlistId = tourListRepository.findByTourName(tagName).getTourlistId();
        if (tourlistId != null) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setTagName(tagName);
            tagEntity.setTourListEntity(tourListRepository.findByTourName(tagName));
            tagEntity.setBoardEntity(boardRepository.findAllById(boardId));

            tagRepository.save(tagEntity);
        }
    }
}