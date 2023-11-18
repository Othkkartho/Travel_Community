package com.learn.travel_community.domain.tour;

import com.learn.travel_community.domain.board.BoardEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name")
    private String Name;

    @ManyToOne
    @JoinColumn(name = "bid")
    private BoardEntity boardEntity;

    @ManyToOne
    @JoinColumn(name = "tourlistId")
    private TourListEntity tourListEntity;
}