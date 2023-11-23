package com.learn.travel_community.domain.tour;

import com.learn.travel_community.domain.board.BoardEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name")
    private String tagName;

    @Column(name = "tourlistId", updatable = false, insertable = false)
    private Long tourlistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourlistId")
    private TourListEntity tourListEntity;
}