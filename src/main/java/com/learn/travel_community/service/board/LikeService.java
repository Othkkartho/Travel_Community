package com.learn.travel_community.service.board;

import com.learn.travel_community.domain.board.BoardEntity;
import com.learn.travel_community.domain.board.Likes;
import com.learn.travel_community.domain.board.LikesRepository;
import com.learn.travel_community.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikesRepository likesRepository;

    @Transactional
    public void likes(Member member, BoardEntity board) {
        Likes likes = Likes.builder()
                .member(member)
                .board(board)
                .build();

        likesRepository.save(likes);
    }

    @Transactional
    public void dislikes(Member member, BoardEntity board) {
        Likes likes = likesRepository.findByMemberAndBoard(member, board);

        likesRepository.delete(likes);
    }
}
