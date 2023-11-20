package com.learn.travel_community.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "board_recommend")
public class BoardRecommendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int age;

    @Column
    private int gender;

    @Column
    private String theme;

    @OneToMany(mappedBy = "boardRecommendEntity", fetch = FetchType.EAGER)
    private List<RecommendContentEntity> recommendContents;

}