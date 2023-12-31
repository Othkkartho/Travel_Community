package com.learn.travel_community.controller.member;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.board.*;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.domain.tour.ScrapEntity;
import com.learn.travel_community.domain.tour.ScrapRepository;
import com.learn.travel_community.dto.member.MemberDto;
import com.learn.travel_community.dto.member.ProfileImgDto;
import com.learn.travel_community.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final HttpSession httpSession;
    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ScrapRepository scrapRepository;

    private static final String ATTRIBUTENAME = "member";
    private static final String REDIRECTHOME = "redirect:/";

    @GetMapping(value="/profile")
    public String getMyProfile(Model model) {
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        assert member != null;
        List<Likes> likesList = likesRepository.findAllByMemberOrderByCreatedTimeDesc(member);
        List<BoardEntity> boardEntityList = boardRepository.findTop10ByMemberOrderByCreatedTimeDesc(member);
        List<CommentEntity> commentEntityList = commentRepository.findTop10ByMemberOrderByCreatedTimeDesc(member);
        List<ScrapEntity> scrapEntityList = scrapRepository.findAllByMember(member);

        Long likesCount = likesRepository.getMemberLikesCount(member.getUid());

        model.addAttribute(ATTRIBUTENAME, member);
        model.addAttribute("likesList", likesList);
        model.addAttribute("boardList", boardEntityList);
        model.addAttribute("commentList", commentEntityList);
        model.addAttribute("scrapList", scrapEntityList);
        model.addAttribute("likeCount", likesCount);

        return "member/profile";
    }

    @GetMapping(value="/profile/{uid}")
    public String getOthersProfile(@PathVariable String uid, Model model) {
        Member ownMember = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        Member member = memberRepository.findById(Long.parseLong(uid)).orElse(null);
        Long likesCount = likesRepository.getMemberLikesCount(member.getUid());
        List<Likes> likesList = likesRepository.findAllByMemberOrderByCreatedTimeDesc(member);
        List<BoardEntity> boardEntityList = boardRepository.findTop10ByMemberOrderByCreatedTimeDesc(member);
        List<CommentEntity> commentEntityList = commentRepository.findTop10ByMemberOrderByCreatedTimeDesc(member);
        List<ScrapEntity> scrapEntityList = scrapRepository.findAllByMember(member);

        if (ownMember != null) {
            model.addAttribute("userName", ownMember.getNickname());
            model.addAttribute("profileImg", ownMember.getPicture());
        }

        model.addAttribute(ATTRIBUTENAME, member);
        model.addAttribute("likesList", likesList);
        model.addAttribute("boardList", boardEntityList);
        model.addAttribute("commentList", commentEntityList);
        model.addAttribute("scrapList", scrapEntityList);
        model.addAttribute("likeCount", likesCount);

        return "member/profile";
    }

    @GetMapping(value="/edit")
    public String getUsers(Model model) {
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        model.addAttribute(ATTRIBUTENAME, member);

        return "member/edit";
    }

    @PostMapping( "/modify")
    public String memberModify(@ModelAttribute MemberDto memberDto) {
        memberService.memberModify(memberDto);

        return REDIRECTHOME;
    }

    @GetMapping("/profileImg/modify")
    public String getImgChange() {
        return "member/profileImg";
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
