package com.learn.travel_community.config.member.oauth.dto;

import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    private String nickname;
    private String picture;
    private String socialId;
    private String accessToken;

    private static final String ANONYMOUS = "/profile/anonymous.png";
    private static final String PUTEMAIL = "email";

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String email, String nickname, String picture, String socialId, String accessToken) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.email = email;
        this.nickname = nickname;
        this.picture = picture;
        this.socialId = socialId;
        this.accessToken = accessToken;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        if ("naver".equals(registrationId))
            return ofNaver("id", attributes, accessToken);
        else if ("kakao".equals(registrationId))
            return ofKakao("id", attributes, accessToken);

        return ofGoogle(userNameAttributeName, attributes, accessToken);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        return OAuthAttributes.builder()
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get(PUTEMAIL))
                .picture(ANONYMOUS)
                .socialId("google")
                .accessToken(accessToken)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .nickname((String) response.get("nickname"))
                .email((String) response.get(PUTEMAIL))
                .picture(ANONYMOUS)
                .socialId("naver")
                .accessToken(accessToken)
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");

        return OAuthAttributes.builder()
                .nickname((String) profile.get("nickname"))
                .email((String) response.get(PUTEMAIL))
                .picture(ANONYMOUS)
                .socialId("kakao")
                .accessToken(accessToken)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .picture(picture)
                .socialId(socialId)
                .accessToken(accessToken)
                .role(Role.USER)
                .build();
    }
}
