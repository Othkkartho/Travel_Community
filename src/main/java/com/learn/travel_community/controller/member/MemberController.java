package com.learn.travel_community.controller.member;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.member.MemberDto;
import com.learn.travel_community.dto.member.ProfileImgDto;
import com.learn.travel_community.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final HttpSession httpSession;

    private static final String ATTRIBUTENAME = "member";
    private static final String REDIRECTHOME = "redirect:/";

    @GetMapping(value="/profile")
    public String getMyProfile(Model model) {
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        model.addAttribute(ATTRIBUTENAME, member);

        return "/member/profile";
    }

    @GetMapping(value="/profile/{uid}")
    public String getOthersProfile(@PathVariable String uid, Model model) {
        Member member = memberRepository.findById(Long.parseLong(uid)).orElse(null);
        model.addAttribute(ATTRIBUTENAME, member);

        return "/member/profile";
    }

    @GetMapping(value="/edit")
    public String getUsers(Model model) {
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        model.addAttribute(ATTRIBUTENAME, member);

        return "/member/edit";
    }

    @PostMapping( "/modify")
    public String memberModify(@ModelAttribute MemberDto memberDto) {
        memberService.memberModify(memberDto);

        return REDIRECTHOME;
    }

    @GetMapping("/profileImg/modify")
    public String getImgChange() {
        return "/member/profileImg";
    }

    @PostMapping("/profileImg/modify")
    public String imgChange(@ModelAttribute ProfileImgDto profileImgDto) {
        memberService.profileImgChange(profileImgDto, ((SessionMember) httpSession.getAttribute("user")).getEmail());

        return REDIRECTHOME;
    }

    @GetMapping("/profileImg/remove")
    public String removeProfileImg() {
        memberService.profileImgRemove(((SessionMember) httpSession.getAttribute("user")).getEmail());

        return REDIRECTHOME;
    }

    @GetMapping("/remove")
    public String memberDelete() {
        memberService.memberRemove(((SessionMember) httpSession.getAttribute("user")).getEmail());

        return "redirect:/logout";
    }
}
