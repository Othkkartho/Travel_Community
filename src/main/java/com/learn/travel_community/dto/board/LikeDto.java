package com.learn.travel_community.dto.board;

import lombok.Getter;

@Getter
public class LikeDto {
    private Long bid;
    private Long likeCount;

    public LikeDto(Long bid, Long likeCount) {
        this.bid = bid;
        this.likeCount = likeCount;
    }
}
