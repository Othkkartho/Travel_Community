package com.learn.travel_community.domain.tour;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "topdata")
public class TopDataEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column
        private Integer rankNo;

        @Column
        private Integer traffic;

        @Column
        private Date date;

        @Column(insertable=false, updatable=false)
        private Long tourlistId;

        @ManyToOne
        @JoinColumn(name = "tourlistId")
        private TourListEntity tourListEntity;
}
