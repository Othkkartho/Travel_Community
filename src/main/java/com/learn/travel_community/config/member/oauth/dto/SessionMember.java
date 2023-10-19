package com.learn.travel_community.config.member.oauth.dto;

import com.learn.travel_community.domain.member.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String email;
    private String nickname;
    private String picture;

    public SessionMember(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.picture = member.getPicture();
    }
}
