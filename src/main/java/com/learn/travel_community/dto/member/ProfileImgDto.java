package com.learn.travel_community.dto.member;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImgDto {
    private MultipartFile profileImg;
}
