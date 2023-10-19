package com.learn.travel_community.service.member;

import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

// 쇼셜 회원 연결을 끊기 위해 남겨놓은 클래스
@Slf4j
@RequiredArgsConstructor
@Service
public class RevokeService {
    private final MemberRepository memberRepository;
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    public void deleteGoogleAccount(String accessToken) {
        Member member = extractUserFromAccessToken(accessToken);
        deleteMember(member);

        String data = "token=" + member.getAccessToken();

        sendRevokeRequest(data, "GOOGLE", null);
    }

    public void deleteNaverAccount(String accessToken) {
        Member member = extractUserFromAccessToken(accessToken);
        deleteMember(member);

        String data = "client_id=" + naverClientId +
                "&client_secret=" + naverClientSecret +
                "&access_token=" + member.getAccessToken() +
                "&service_provider=NAVER" +
                "&grant_type=delete";

        sendRevokeRequest(data, "NAVER", null);
    }

    public void deleteKakaoAccount(String accessToken) {
        Member member = extractUserFromAccessToken(accessToken);
        deleteMember(member);

        sendRevokeRequest(null, "KAKAO", member.getAccessToken());
    }

    private Member extractUserFromAccessToken(String accessToken) {
        Optional<Member> member = memberRepository.findByAccessToken(accessToken);

        return member.get();
    }

    private void deleteMember(Member member) {
        // 유저 관련 데이터 DB에서 삭제
        memberRepository.deleteById(member.getUid());
    }

    /**
     * @param data : revoke request의 body에 들어갈 데이터
     * @param provider : oauth2 업체
     * @param accessToken : 카카오의 경우 url이 아니라 헤더에 access token을 첨부해서 보내줘야 함
     */
    private void sendRevokeRequest(String data, String provider, String accessToken) {
        String googleRevokeUrl = "https://accounts.google.com/o/oauth2/revoke";
        String naverRevokeUrl = "https://nid.naver.com/oauth2.0/token";
        String kakaoRevokeUrl = "https://kapi.kakao.com/v1/user/unlink";

        RestTemplate restTemplate = new RestTemplate();
        String revokeUrl = "";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(data, headers);

        switch (provider) {
            case "GOOGLE" -> revokeUrl = googleRevokeUrl;
            case "NAVER" -> revokeUrl = naverRevokeUrl;
            default -> {
                revokeUrl = kakaoRevokeUrl;
                headers.setBearerAuth(accessToken);
            }
        }

        restTemplate.exchange(revokeUrl, HttpMethod.POST, entity, String.class);
    }
}
