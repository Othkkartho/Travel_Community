package com.learn.travel_community.domain.tour;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ScrapId implements Serializable {
    @Column(name = "uid")
    private Long uid;

    @Column(name = "detail_id")
    private Long detailId;

    public ScrapId() {

    }

    public ScrapId(Long uid, Long detailId) {
        this.uid = uid;
        this.detailId = detailId;
    }
}