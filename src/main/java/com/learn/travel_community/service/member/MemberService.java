package com.learn.travel_community.service.member;
;
import com.learn.travel_community.dto.member.MemberDto;

public interface MemberService {
    void memberModify(MemberDto memberDto);
    void memberRemove(String email);
}
