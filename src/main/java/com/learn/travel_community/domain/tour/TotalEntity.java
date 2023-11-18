package com.learn.travel_community.domain.tour;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "total")
@ToString
public class TotalEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column
        private String gender;

        @Column
        private Integer ageGroup;

        @Column
        private Date investigationDate;

        @Column
        private Float region_interest_score;

        @Column(insertable=false, updatable=false)
        private Long tourlistId;

        @ManyToOne
        @JoinColumn(name = "tourlistId")
        private TourListEntity tourListEntity;
}
