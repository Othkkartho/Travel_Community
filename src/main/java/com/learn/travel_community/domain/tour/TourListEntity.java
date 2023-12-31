package com.learn.travel_community.domain.tour;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tourlist")
public class TourListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tourlistId;

    @Column(columnDefinition = "BLOB")
    private String tourImage;

    @Column(length = 200)
    private String tourName;

    @Column(length = 500)
    private String tourExp;

    @Column(insertable=false, updatable=false)
    private Long countryId;

    @OneToMany(mappedBy = "tourListEntity", fetch = FetchType.EAGER)
    private List<TagEntity> tagEntityList;

    @OneToMany(mappedBy = "tourListEntity", fetch = FetchType.EAGER)
    private List<TourdetailEntity> tourdetailEntityList;

    @OneToMany(mappedBy = "tourListEntity", fetch = FetchType.EAGER)
    private List<TopDataEntity> topDataEntitiyList;

    @ManyToOne
    @JoinColumn(name = "countryId", nullable = false)
    private CountryEntity countryEntity;

}