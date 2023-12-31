package com.learn.travel_community.domain.tour;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "country")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @Column
    private String countryName;

    @OneToMany(mappedBy = "countryEntity", fetch = FetchType.EAGER)
    private List<TourListEntity> tourListEntityList;
}
