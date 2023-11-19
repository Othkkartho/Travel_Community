package com.learn.travel_community.domain.board;

import com.learn.travel_community.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "recommend_content")
public class RecommendContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(length = 250)
    private String introduce;

    @Column(length = 500)
    private String recommendContents;

    @Column
    private String image;

    @Column(insertable = false, updatable = false)
    private Long boardRecommendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardRecommendId")
    private BoardRecommendEntity boardRecommendEntity;

}
