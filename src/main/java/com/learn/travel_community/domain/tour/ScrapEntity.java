package com.learn.travel_community.domain.tour;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.learn.travel_community.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scrap")
public class ScrapEntity implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long sid;

    @Column(insertable=false, updatable=false)
    private Long detailId;

    @Column(insertable=false, updatable=false)
    private Long uid;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "detail_id")
    private TourdetailEntity detailEntity;
}