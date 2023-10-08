package com.learn.travel_community.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    BRONZE("ROLE_BRONZE", "브론즈 티어 회원"),
    SILVER("ROLE_SILVER", "실버 티어 회원"),
    GOLD("ROLE_GOLD", "골드 티어 회원"),
    PLATINUM("ROLE_PLATINUM", "플레티넘 티어 회원"),
    MANAGER("ROLE_MANAGER", "사이트 메니저"),
    ADMIN("ROLE_ADMIN", "서버 관리자");

    private final String key;
    private final String title;
}
