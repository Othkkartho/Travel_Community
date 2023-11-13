package com.learn.travel_community.controller.tour;

import com.learn.travel_community.domain.tour.TourdetailEntity;
import com.learn.travel_community.dto.tour.TourDetailDto;
import com.learn.travel_community.dto.tour.TourListDto;
import com.learn.travel_community.service.tour.TourListService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourListController {
    private final TourListService tourListService;

    @GetMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "1") Long countryId, Model model) {
        List<TourListDto> tourListDtos = tourListService.findAllByCountryId(countryId);
        model.addAttribute("tourListDtos", tourListDtos);

        return "tour/search";
    }

    @GetMapping("/detail/{tourlistId}")
    public String detail(@PathVariable Long detailId, Model model) {
        TourDetailDto tourDetailDto = tourListService.findByDetailId(detailId);
        model.addAttribute("tourDetailDto", tourDetailDto);

        return "tour/detail";
    }
}