package com.learn.travel_community.domain.tour;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "topdata")
public class TopDataEntity {
    @Id
    private Long rank;

    @Column
    private Long traffic;

    @Column
    private Date date;

    @Column(insertable=false, updatable=false)
    private Long tourlistId;

    @ManyToOne
    @JoinColumn(name = "tourlistId")
    private TourListEntity tourListEntity;

}
