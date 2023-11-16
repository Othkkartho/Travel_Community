package com.learn.travel_community.domain.tour;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "topdata")
public class TopDataEntity {
    @Id
    private Long rank;

    @Column
    private String region;

    @Column
    private Long traffic;

    @Column
    private Date Date;
}
