package com.learn.travel_community.dto.member;

import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.Role;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class MemberDto {
    private String email;
    private String nickname;
    private String picture;
    private int age;
    private int gender;
    private String introduce;

    public MemberDto(String email, String nickname, String picture, int age, int gender, String introduce) {
        this.email = email;
        this.nickname = nickname;
        this.picture = picture;
        this.age = age;
        this.gender = gender;
        this.introduce = introduce;
    }

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}
