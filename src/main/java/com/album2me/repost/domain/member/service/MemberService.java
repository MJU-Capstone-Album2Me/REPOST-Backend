package com.album2me.repost.domain.member.service;

import com.album2me.repost.domain.member.repository.MemberRepository;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void checkAlreadyJoined(Room room, User user) {
        if(memberRepository.existsByRoomAndUser(room, user)){
            throw new IllegalArgumentException("이미 Room에 가입되어 있습니다.");
        }
    }

}
