package com.learn.travel_community.controller.board;

import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.board.BoardRecommendDto;
import com.learn.travel_community.service.board.boardRecommendService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class RecommendController {
    private final boardRecommendService boardRecommendService;

    @Transactional
    @GetMapping("/community/Community_recommend/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        BoardRecommendDto boardRecommendDTO = boardRecommendService.findOne(id);
        ModelAndView modelAndView = new ModelAndView("Community_recommend");
        modelAndView.addObject("boardRecommendDTO", boardRecommendDTO);
        return modelAndView;
    }
}
