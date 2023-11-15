package com.learn.travel_community.controller.board;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.board.*;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.board.BoardDTO;
import com.learn.travel_community.dto.board.CommentDTO;
import com.learn.travel_community.dto.board.LikeDto;
import com.learn.travel_community.service.board.BoardService;
import com.learn.travel_community.service.board.CommentService;
import com.learn.travel_community.service.board.LikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final MemberRepository memberRepository;
    private final BoardService boardService;
    private final CommentService commentService;
    private final HttpSession httpSession;
    private final BoardRepository boardRepository;
    private final LikeService likeService;
    private final LikesRepository likesRepository;
    private final ViewRepository viewRepository;

    @GetMapping("/save")
    public String saveForm(Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("user");
        model.addAttribute("userName", member.getNickname());
        model.addAttribute("profileImg", member.getPicture());

        return "/community/Community_write";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        boardService.save(member, boardDTO);

        return "redirect:/board/paging";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, @PageableDefault(page=1) Pageable pageable) {
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        BoardEntity board = boardRepository.findById(id).orElseThrow();

        if (viewRepository.findByMemberAndBoard(member, board) == null) {
            Viewer viewer = Viewer.builder()
                    .member(member)
                    .board(board)
                    .build();

            viewRepository.save(viewer);
        }

        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("dolike", likesRepository.findByMemberAndBoard(member, board));

        return "/community/Community_detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "/community/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        BoardDTO board = boardService.update(member, boardDTO);
        model.addAttribute("board", board);
        Long id = boardDTO.getId();
        return "redirect:/board/" + id;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow();
        boardService.delete(boardEntity.getMember().getUid(), id);
        return "redirect:/board/paging";
    }

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boardList = boardService.paging(pageable);
        List<LikeDto> likesList = likesRepository.getBoardsLikesCount();
        Map<Long, Long> likeMap = new HashMap<>();

        for (LikeDto l: likesList) {
            likeMap.put(l.getBid(), l.getLikeCount());
        }

        int blockLimit = 5;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = 0;
        if ((startPage + blockLimit - 1) < boardList.getTotalPages())
            endPage = startPage + blockLimit - 1;
        else
            endPage = boardList.getTotalPages() == 0 ? -1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("likeCount", likeMap);

        return "/community/Community_main";
    }

    @GetMapping("/like/{id}")
    public String like(@PathVariable Long id) {
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        BoardEntity board = boardRepository.findById(id).orElseThrow();
        likeService.likes(member, board);

        return "redirect:/board/" + id;
    }

    @GetMapping("/dislike/{id}")
    public String dislike(@PathVariable Long id) {
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        BoardEntity board = boardRepository.findById(id).orElseThrow();
        likeService.dislikes(member, board);

        return "redirect:/board/" + id;
    }
}