package com.learn.travel_community.controller.tour;

import com.learn.travel_community.dto.tour.TourListDto;
import com.learn.travel_community.service.tour.TourListService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/tour")
public class TourListController {
    private final TourListService tourListService;

    public TourListController(TourListService tourListService) {
        this.tourListService = tourListService;
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "1") Long countryId, Model model) {
        List<TourListDto> tourListDtos = tourListService.findAllByCountryId(countryId);
        model.addAttribute("tourListDtos", tourListDtos);

        return "tour/search";
    }

    @GetMapping("/search/{tourlistId}")
    public ResponseEntity<TourListDto> findByTourlistId(@PathVariable Long tourlistId) {
        TourListDto tourListDto = tourListService.findByTourlistId(tourlistId);
        return ResponseEntity.ok(tourListDto);
    }
}