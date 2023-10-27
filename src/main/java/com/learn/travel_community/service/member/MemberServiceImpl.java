package com.learn.travel_community.service.member;

import com.learn.travel_community.config.member.oauth.dto.SessionMember;
import com.learn.travel_community.domain.member.Member;
import com.learn.travel_community.domain.member.MemberRepository;
import com.learn.travel_community.dto.member.MemberDto;
import com.learn.travel_community.dto.member.ProfileImgDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    private final RevokeService revokeService;

    private static final String ANONYMOUS = "/profile/profile.png";

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
        Member member = memberRepository.findByEmail(email).orElse(null);
        switch (Objects.requireNonNull(member).getSocialId()) {
            case "google" -> revokeService.deleteGoogleAccount(member.getAccessToken());
            case "naver" -> revokeService.deleteNaverAccount(member.getAccessToken());
            default -> revokeService.deleteKakaoAccount(member.getAccessToken());
        }
        memberRepository.deleteById(member.getUid());
    }

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    @Override
    public void profileImgChange(ProfileImgDto profileImgDto, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
        if (!member.getPicture().equals(ANONYMOUS)) {
            try {
                Files.delete(Path.of(uploadFolder + member.getPicture()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        MultipartFile file = profileImgDto.getProfileImg();

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + file.getOriginalFilename();

        File destinationFile = new File(uploadFolder + "profile/" + imageFileName);

        try {
            file.transferTo(destinationFile);

            member.updatePicture("/profile/" + imageFileName);

            httpSession.setAttribute("user", new SessionMember(member));
            memberRepository.save(member);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void profileImgRemove(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));

        if (member.getPicture().equals(ANONYMOUS)) {
            return;
        }

        try {
            Files.delete(Path.of(uploadFolder + member.getPicture()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        member.updatePicture(ANONYMOUS);

        httpSession.setAttribute("user", new SessionMember(member));
        memberRepository.save(member);
    }
}
