package com.learn.travel_community.controller.tour;

import com.learn.travel_community.domain.tour.TourDetailRepository;
import com.learn.travel_community.domain.tour.TourListRepository;
import com.learn.travel_community.dto.tour.TourDetailDto;
import com.learn.travel_community.dto.tour.TourListDto;
import com.learn.travel_community.service.tour.TourListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourListController {
    private final TourListService tourListService;
    @Autowired
    private final TourListRepository tourListRepository;
    @Autowired
    private final TourDetailRepository tourDetailRepository;
    @GetMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "1") Long countryId, Model model) {
        List<TourListDto> tourListDtos = tourListService.search(countryId);

        model.addAttribute("tourListDtos", tourListDtos);

        return "tour/search";
    }

        @GetMapping("/detail/{detailId}")
        public String detail(@PathVariable Long detailId, Model model) {
            TourDetailDto tourDetailDto = tourListService.findAllByDetailId(detailId);
            model.addAttribute("tourDetailDto", tourDetailDto);

            return "tour/detail";
        }
}