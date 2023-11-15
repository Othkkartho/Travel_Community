package com.learn.travel_community.controller.tour;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.domain.tour.*;
import com.learn.travel_community.dto.BasicResponse;
import com.learn.travel_community.repository.board.BoardRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 3600)
@RestController
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
        for (int i = 0; i < scrapList.size(); i++)
            tourDetailEntityList.add(scrapList.get(i).getDetailEntity());

        result.status = true;
        result.data = "success";
        result.object = tourDetailEntityList;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/user/scrap/") // Post Mapping 스크랩 추가
    public Object userScrapAdd(@RequestParam final String detailId) throws Exception {
        final BasicResponse result = new BasicResponse();
        final ScrapEntity scrapEntity = new ScrapEntity();
        Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
        final TourdetailEntity tourdetailEntity = tourDetailRepository.findAllByDetailId(Long.valueOf(detailId));

        scrapEntity.setMember(member);
        scrapEntity.setDetailEntity(tourdetailEntity);
        scrapEntity.setUid(member.getUid());
        scrapEntity.setDetail_id(tourdetailEntity.getDetailId());
        scrapRepository.save(scrapEntity);

        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/user/scrap/") // Delete Mapping 필요 스크랩 취소
    public Object userScrapRemove(@RequestParam final String username, @RequestParam final Long detailId) throws Exception {
        final BasicResponse result = new BasicResponse();
        final ScrapEntity deleteScrap = new ScrapEntity();
        final Member member = memberRepository.getMemberByNickname(username).orElseThrow(Exception::new);
        final TourdetailEntity tourdetailEntity = tourDetailRepository.getOne(detailId);
        deleteScrap.setMember(member);
        deleteScrap.setDetailEntity(tourdetailEntity);
        scrapRepository.delete(deleteScrap);

        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
