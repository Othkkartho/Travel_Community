package com.learn.travel_community.controller.tour;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.domain.tour.ScrapEntity;
import com.learn.travel_community.domain.tour.ScrapRepository;
import com.learn.travel_community.domain.tour.TourDetailRepository;
import com.learn.travel_community.domain.tour.TourdetailEntity;
import com.learn.travel_community.dto.BasicResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 3600)
@Controller
public class ScrapController {
    @Autowired
    ScrapRepository scrapRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TourDetailRepository tourDetailRepository;
    private final HttpSession httpSession;


    @GetMapping("/user/scrap/") // Get Mapping 필요 스크랩 목록
    public Object scrapList(@RequestParam final String username) throws Exception {
        final Member member = memberRepository.getMemberByNickname(username).orElseThrow(Exception::new);
        final List<ScrapEntity> scrapList = scrapRepository.findAllByUid(member.getUid());

        final BasicResponse result = new BasicResponse();
        final List<TourdetailEntity> tourDetailEntityList = new ArrayList<>();
        for (ScrapEntity scrapEntity : scrapList) {
            tourDetailEntityList.add(scrapEntity.getDetailEntity());
        }

        result.status = true;
        result.data = "success";
        result.object = tourDetailEntityList;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/user/scrap/") // Post Mapping 스크랩 추가
    public String userScrapAdd(@RequestParam final String detailId) {
        final BasicResponse result = new BasicResponse();
        final ScrapEntity scrapEntity = new ScrapEntity();
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        final TourdetailEntity tourdetailEntity = tourDetailRepository.findAllByDetailId(Long.valueOf(detailId));

        scrapEntity.setMember(member);
        scrapEntity.setDetailEntity(tourdetailEntity);
        scrapEntity.setUid(member.getUid());
        scrapEntity.setDetailId(tourdetailEntity.getDetailId());
        scrapRepository.save(scrapEntity);

        result.status = true;
        result.data = "success";
        return "redirect:/tour/search";
    }

    @DeleteMapping("/user/scrap/") // Delete Mapping 필요 스크랩 취소
    public String userScrapRemove(@RequestParam final String scrapId) {
        final BasicResponse result = new BasicResponse();
        scrapRepository.deleteBySid(Long.valueOf(scrapId));

        result.status = true;
        result.data = "success";
        return "redirect:/tour/search";
    }
}
