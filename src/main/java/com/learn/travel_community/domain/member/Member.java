package com.learn.travel_community.domain.member;

import com.learn.travel_community.domain.BaseTimeEntity;
import com.learn.travel_community.dto.member.MemberDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Column
    private String picture;

    @Column()
    private int age;    // 나이대 10, 20, 30 등 저장, 0은 비공개

    @Column()
    private int gender; // 0: 비공개, 1: 남성, 2: 여성

    @Column(length = 500)
    private String introduce;

    @Column(length = 10)
    private String socialId;
    @Column()
    private String accessToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(String email, String nickname, String picture, int age, int gender, String introduce, String socialId, String accessToken, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.picture = picture;
        this.age = age;
        this.gender = gender;
        this.introduce = introduce;
        this.socialId = socialId;
        this.accessToken = accessToken;
        this.role = role;
    }

    public Member update(String nickname, String picture, String accessToken) {
        this.nickname = nickname;
        this.picture = picture;
        this.accessToken = accessToken;

        return this;
    }

    public Member modify(MemberDto memberDto) {
        this.nickname = memberDto.getNickname();
        this.picture = memberDto.getPicture();
        this.age = memberDto.getAge();
        this.gender = memberDto.getGender();
        this.introduce = memberDto.getIntroduce();

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
