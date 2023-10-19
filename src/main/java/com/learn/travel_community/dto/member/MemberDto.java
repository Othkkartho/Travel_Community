package com.learn.travel_community.dto.member;

import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.Role;
import lombok.Getter;

@Getter
public class MemberDto {
    private String email;
    private String nickname;
    private int age;
    private int gender;
    private String introduce;

    public MemberDto(String email, String nickname, int age, int gender, String introduce) {
        this.email = email;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.introduce = introduce;
    }

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .age(age)
                .gender(gender)
                .introduce(introduce)
                .role(Role.USER)
                .build();
    }
}
