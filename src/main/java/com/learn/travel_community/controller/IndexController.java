package com.learn.travel_community.controller;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("user");
        if (member != null) {
            model.addAttribute("userName", member.getNickname());
            model.addAttribute("profileImg", member.getPicture());
        }

        return "index";
    }
}
