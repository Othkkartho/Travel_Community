package com.learn.travel_community.dto.tour;

import com.learn.travel_community.domain.tour.CountryEntity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@Data
public class CountryDto {
    private Long countryId;
    private String countryName;

    public CountryDto(Long countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public static CountryDto toCountryDto(CountryEntity countryEntity) {
        CountryDto countryDto = new CountryDto();
        countryDto.setCountryId(countryEntity.getCountryId());
        countryDto.setCountryName(countryEntity.getCountryName());

        return countryDto;
    }

}