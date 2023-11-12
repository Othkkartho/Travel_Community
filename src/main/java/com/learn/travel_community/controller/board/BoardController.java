package com.learn.travel_community.controller.board;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.board.BoardDTO;
import com.learn.travel_community.dto.board.CommentDTO;
import com.learn.travel_community.repository.board.BoardRepository;
import com.learn.travel_community.service.board.BoardService;
import com.learn.travel_community.service.board.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final MemberRepository memberRepository;
    private final BoardService boardService;
    private final CommentService commentService;
    private final HttpSession httpSession;
    private final BoardRepository boardRepository;

    @GetMapping("/save")
    public String saveForm(Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("user");
        model.addAttribute("userName", member.getNickname());
        model.addAttribute("profileImg", member.getPicture());
        return "/board/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        boardService.save(member, boardDTO);

        return "redirect:/board/paging";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                            @PageableDefault(page=1) Pageable pageable) {
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "board/detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "board/update";
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
        int blockLimit = 5;
        int startPage = (((int)(Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages() == 0 ? -1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/paging";
    }

}
