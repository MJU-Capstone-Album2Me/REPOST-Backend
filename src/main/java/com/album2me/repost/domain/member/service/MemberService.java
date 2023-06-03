package com.album2me.repost.domain.member.service;

import com.album2me.repost.domain.member.domain.Member;
import com.album2me.repost.domain.member.repository.MemberRepository;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.user.model.User;

import java.util.List;
import java.util.NoSuchElementException;
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

    public void checkHost(Room room, User user) {
        Member member = findMemberByRoomAndUser(room, user);
        if(!member.isHost()) {
            throw new IllegalArgumentException("해당 권한이 없습니다.");
        }
    }
    public Member findMemberByRoomAndUser(Room room, User user) {
        return memberRepository.findByRoomAndUser(room, user)
                .orElseThrow(() -> new NoSuchElementException("해당 Room에 가입되어있지 않습니다."));
    }

    public List<Member> findMembersByUser(User user) {
        return memberRepository.findMembersWithRoomByUser(user);
    }
}
