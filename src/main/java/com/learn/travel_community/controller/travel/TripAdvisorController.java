package com.learn.travel_community.controller.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.travel_community.domain.travel.TripAdvisorRepository;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/travel")
public class TripAdvisorController {
//    @Autowired
    @Resource(name="tripAdviserService")
    private TripAdvisorRepository tripAdvisorRepository;

    @PostMapping("/tripAdvisor")
    @ResponseBody
    public void tripAdvisor(@RequestParam Map<String, Object> request, HttpServletResponse response) throws IOException {
//        log.info("*********** {} ***********", "TripAdvisorController tripAdvisor Start");

        List<Map<String, Object>> resultList = tripAdvisorRepository.getTripAdvisor(request);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writeValueAsString(resultList);

        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();

//        log.info("*********** {} ***********", "TripAdvisorController tripAdvisor End");

        writer.print(jsonResult);
    }
}
