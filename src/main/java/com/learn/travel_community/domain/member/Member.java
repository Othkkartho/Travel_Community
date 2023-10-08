package com.learn.travel_community.domain.member;

import com.learn.travel_community.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    @Column(length = 10)
    private String age = "0";

    @Column(length = 5)
    private String gender = "0";

    @Column(length = 500)
    private String introduce = "자기소개를 작성하시지 않았습니다";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(String email, String nickname, String picture, String age, String gender, String introduce, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.picture = picture;
        this.age = age;
        this.gender = gender;
        this.introduce = introduce;
        this.role = role;
    }

    public Member update(String nickname, String picture) {
        this.nickname = nickname;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
