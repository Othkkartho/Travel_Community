package com.learn.travel_community.controller.member;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.member.MemberDto;
import com.learn.travel_community.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final HttpSession httpSession;

    @GetMapping(value="/edit")
    public String getUsers(Model model) {
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        model.addAttribute("member", member);

        return "/member/edit";
    }

    @PostMapping( "/modify")
    public String memberModify(MemberDto memberDto) {
        memberService.memberModify(memberDto);

        return "redirect:/";
    }

    @GetMapping("/remove")
    public String memberDelete() {
        memberService.memberRemove(((SessionMember) httpSession.getAttribute("user")).getEmail());

        return "redirect:/logout";
    }
}
