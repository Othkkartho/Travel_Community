package com.learn.travel_community.service.member;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.member.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Transactional
    @Override
    public void memberModify(MemberDto memberDto) {
        Member member = memberRepository.findByEmail(memberDto.getEmail())
                .map(entity -> entity.modify(memberDto))
                .orElse(memberDto.toEntity());

        httpSession.setAttribute("user", new SessionMember(member));

        memberRepository.save(member);
    }

    @Override
    public void memberRemove(String email) {
        memberRepository.deleteById(Objects.requireNonNull(memberRepository.findByEmail(email).orElse(null)).getUid());
    }
}