package com.learn.travel_community.service.member;
;
import com.learn.travel_community.dto.member.MemberDto;
import com.learn.travel_community.dto.member.ProfileImgDto;

public interface MemberService {
    void memberModify(MemberDto memberDto);
    void memberRemove(String email);

    void profileImgChange(ProfileImgDto profileImgDto, String email);
    void profileImgRemove(String email);
}
