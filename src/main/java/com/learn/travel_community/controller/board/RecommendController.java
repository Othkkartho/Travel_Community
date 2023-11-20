package com.learn.travel_community.controller.board;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.dto.board.BoardRecommendDto;
import com.learn.travel_community.service.board.boardRecommendService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(name = "/recommend")
public class RecommendController {
    private final boardRecommendService boardRecommendService;
    private final HttpSession httpSession;

    @Transactional
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("user");

        BoardRecommendDto boardRecommendDTO = boardRecommendService.findOne(id);
        model.addAttribute("boardRecommendDTO", boardRecommendDTO);
        return "/community/Community_recommend";
    }
}
