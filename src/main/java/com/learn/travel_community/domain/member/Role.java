package com.learn.travel_community.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "사이트 사용자"),
    MANAGER("ROLE_MANAGER", "사이트 메니저"),
    ADMIN("ROLE_ADMIN", "서버 관리자");

    private final String key;
    private final String title;
}
