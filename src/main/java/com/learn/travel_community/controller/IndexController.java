package com.learn.travel_community.controller;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.board.BoardDTO;
import com.learn.travel_community.dto.board.BoardRecommendDto;
import com.learn.travel_community.service.board.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final HttpSession httpSession;
    private final BoardService boardService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String index(Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("user");
        int age = 20;
        int gender = 1;

        if (member != null) {
            age = memberRepository.findByEmail(member.getEmail()).get().getAge();
            gender = memberRepository.findByEmail(member.getEmail()).get().getGender();
            if(gender == 0) { gender = 1; }
            if(age == 0) { age = 20; }
            model.addAttribute("userName", member.getNickname());
            model.addAttribute("profileImg", member.getPicture());
        }

        List<BoardDTO> boardRecommendDtos = boardService.findRecommendList(age, gender);
        model.addAttribute("boardRecommendDTOs", boardRecommendDtos);

        return "index";
    }
}
