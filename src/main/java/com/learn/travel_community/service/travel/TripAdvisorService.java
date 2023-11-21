package com.learn.travel_community.service.travel;

import com.learn.travel_community.domain.travel.TripAdvisorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service("tripAdviserService")
public class TripAdvisorService implements TripAdvisorRepository {
    @Override
    public List<Map<String, Object>> getTripAdvisor(Map<String, Object> param) {
//        log.info("*********** {} ***********", "TripAdvisorService getTripAdvisor Start");
        String sql = "SELECT t.age_group, t.region_interest_score, tl.tour_name" +
                " FROM total as t" +
                " join tourlist as tl" +
                " on t.tourlist_id = tl.tourlist_id" +
                " where t.gender = '" + param.get("gender") + "'";

        BaseDB db = new BaseDB();

        List<Map<String, Object>> resultList = db.baseDB(sql);
//        log.info("*********** {} ***********", "TripAdvisorService getTripAdvisor End");

        return resultList;
    }
}