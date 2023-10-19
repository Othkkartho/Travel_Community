package com.learn.travel_community.dto.member;

import lombok.Getter;
import lombok.Value;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.multipart.MultipartFile;

@Getter
@EnableJpaAuditing
@Value
public class ProfileImgDto {
    private MultipartFile profileImg;
}
