package com.learn.travel_community.controller.tour;

import com.learn.travel_community.domain.tour.TourDetailRepository;
import com.learn.travel_community.domain.tour.TourListEntity;
import com.learn.travel_community.domain.tour.TourListRepository;
import com.learn.travel_community.dto.tour.TourDetailDto;
import com.learn.travel_community.dto.tour.TourListDto;
import com.learn.travel_community.service.tour.TourListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourListController {
    @Autowired
    private ResourceLoader resourceLoader;
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
        public String detail(@PathVariable Long detailId, Model model) throws IOException {
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


            return "tour/detail";
        }


}