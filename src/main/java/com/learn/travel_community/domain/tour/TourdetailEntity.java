package com.learn.travel_community.domain.tour;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

        @Column(length = 1000)
        private String detailExp;

        @Column
        private String detailImg;

        @Column
        private Integer rankNo;

        @Column(insertable=false, updatable=false)
        private Long tourlistId;

        @ManyToOne
        @JoinColumn(name = "tourlistId", nullable = false)
        private TourListEntity tourListEntity;
}
