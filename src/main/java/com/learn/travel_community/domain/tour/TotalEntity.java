package com.learn.travel_community.domain.tour;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "topdata")
public class TotalEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column
        private Long gender;

        @Column
        private Long ages;

        @Column
        private Date investigationDate;

        @Column
        private float interest_level;

        @Column(insertable=false, updatable=false)
        private Long tourlistTno;

        @ManyToOne
        @JoinColumn(name = "tourlist_tno")
        private TourListEntity tourListEntity;
}
