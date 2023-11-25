package com.learn.travel_community.controller;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.board.BoardDTO;
import com.learn.travel_community.service.board.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final HttpSession httpSession;
    private final BoardService boardService;
    private final MemberRepository memberRepository;

    @Transactional
    @GetMapping("/")
    public String index(Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("user");
        int age = member == null ? 20 : memberRepository.findByEmail(member.getEmail()).get().getAge();
        int gender = member == null ? 2 : memberRepository.findByEmail(member.getEmail()).get().getGender();
        gender = gender == 0 ? 1 : gender;

        if (member != null) {
            model.addAttribute("userName", member.getNickname());
            model.addAttribute("profileImg", member.getPicture());
        }

        List<BoardDTO> boardRecommendDtos = boardService.findRecommendList(age, gender);
        model.addAttribute("boardRecommendDTOs", boardRecommendDtos);

        return "index";
    }
}
