package com.learn.travel_community.controller.board;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        SessionMember user = (SessionMember) httpSession.getAttribute("user");
        if (user != null) model.addAttribute("userName", user.getNickname());

        return "index";
    }
}
