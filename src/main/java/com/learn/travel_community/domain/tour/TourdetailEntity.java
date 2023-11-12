package com.learn.travel_community.domain.tour;

import com.learn.travel_community.domain.board.BoardEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tourdetail")
public class TourdetailEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long detailId;

        @Column
        private String detailName;

        @Column(length = 200)
        private String detailExp;

        @Column
        private String detailImg;

        @Column
        private String DetailImgUrl;

        @ManyToOne
        @JoinColumn(name = "tourlistId")
        private TourListEntity tourListEntity;

}
