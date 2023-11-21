package com.learn.travel_community.controller.tour;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.domain.tour.TotalEntity;
import com.learn.travel_community.domain.tour.TotalRepository;
import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.domain.tour.TourListRepository;
import com.learn.travel_community.domain.travel.TripAdvisorRepository;
import com.learn.travel_community.dto.tour.TourDetailDto;
import com.learn.travel_community.dto.tour.TourListDto;
import com.learn.travel_community.service.tour.TourListService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourListController {
    private final MemberRepository memberRepository;
    private final TourListService tourListService;
    @Autowired
    private final TourListRepository tourListRepository;
    private final HttpSession httpSession;
    @Autowired
    private final TotalRepository totalRepository;
    @Resource(name="tripAdviserService")
    private TripAdvisorRepository tripAdvisorRepository;

    @GetMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "1") Long countryId, LocalDate date, Model model) throws JsonProcessingException {
        if (httpSession.getAttribute("user") != null) {
            Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
            if (member != null) {
                model.addAttribute("userName", member.getNickname());
                model.addAttribute("profileImg", member.getPicture());
            }
        }

        if (date == null) {
            // 날짜 매개변수가 null이면 현재 날짜로 설정합니다.
            date = LocalDate.now();
        }
        // 날짜에서 연도와 월을 추출합니다.
        LocalDate localDate = date;

        // 국가 ID와 날짜를 기반으로 여행 목록을 검색합니다.
        List<TourListDto> tourListDtos = tourListService.search(countryId, localDate);

        List<Map<String, Object>> resultList = tripAdvisorRepository.getTripAdvisor();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writeValueAsString(resultList);

        model.addAttribute("tourListDtos", tourListDtos);
        model.addAttribute("tourListJson", jsonResult);

        return "tour/Search_main2";
    }

    @GetMapping("/detail/{detailId}")
    public String detail(@PathVariable Long detailId, Model model) {
        if (httpSession.getAttribute("user") != null) {
            Member member = memberRepository.findByEmail(((SessionMember) httpSession.getAttribute("user")).getEmail()).orElse(null);
            if (member != null) {
                model.addAttribute("userName", member.getNickname());
                model.addAttribute("profileImg", member.getPicture());
            }
        }

        TourDetailDto tourDetailDto = tourListService.findAllByDetailId(detailId);
        TourListEntity tourListEntity = tourListRepository.findTourNameByTourlistId(tourDetailDto.getTourlistId());
        String tourName = tourListEntity.getTourName();

        // Load images from both folders
        String naverImageResource = "/images/tour/wc_naver/" + tourName + "_wordcloud.jpg";
        String tripadvisorImageResource = "/images/tour/wc_tripadvisor/" + tourName + "_wordcloud.jpg";

        model.addAttribute("naverImage", naverImageResource);
        model.addAttribute("tripadvisorImage", tripadvisorImageResource);
        model.addAttribute("tourDetailDto", tourDetailDto);
        model.addAttribute("tourName", tourName);

        return "tour/Search_regiondetail";
    }

    @GetMapping("/index/{ageGroup}")
    public String scoreByAgeGroup(@RequestParam(required = false, defaultValue = "20") Integer ageGroup, Model model) {
        List<TotalEntity> totalEntityList = totalRepository.findRegionInterestScoreByAgeGroup(ageGroup);

        model.addAttribute("totalEntity", totalEntityList);
        return "index";
    }
}