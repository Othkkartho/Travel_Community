package com.learn.travel_community.domain.travel;

import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.domain.board.CommentEntity;
import com.learn.travel_community.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface TripAdvisorRepository {
    public List<Map<String, Object>> getTripAdvisor(Map<String, Object> param);

    public List<Map<String, Object>> getTripAdvisor();
}