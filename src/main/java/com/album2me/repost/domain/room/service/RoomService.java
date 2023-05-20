package com.album2me.repost.domain.room.service;

import com.album2me.repost.domain.member.domain.Member;
import com.album2me.repost.domain.member.service.MemberService;
import com.album2me.repost.domain.room.dto.RoomCreateRequest;
import com.album2me.repost.domain.room.dto.RoomCreateResponse;
import com.album2me.repost.domain.room.dto.RoomInviteCodeResponse;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.room.model.RoomApply;
import com.album2me.repost.domain.room.repository.RoomRepository;

import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {
    private final UserService userService;

    private final RoomRepository roomRepository;
    private final MemberService memberService;

    @Transactional
    public RoomCreateResponse createRoom(RoomCreateRequest roomCreateRequest, Long userId) {
        User user = userService.findUserById(userId);
        Room newRoom = new Room(roomCreateRequest.name());
        Member host = new Member(user, newRoom, true);
        newRoom.addMember(host);
        roomRepository.save(newRoom);
        return new RoomCreateResponse(newRoom.getId());
    }

    public RoomInviteCodeResponse getInviteCode(Long roomId) {
        Room room = findRoomById(roomId);
        return new RoomInviteCodeResponse(room.getInviteCode());
    }

    @Transactional
    public void applyRoom(String inviteCode, Long requesterId) {
        Room room = findRoomByInviteCode(inviteCode);
        User requester = userService.findUserById(requesterId);
        memberService.checkAlreadyJoined(room, requester);
        room.addApply(new RoomApply(room, requester));
    }

    public Room findRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 Room을 찾을 수 없습니다."));
    }

    public Room findRoomByInviteCode(String inviteCode) {
        return roomRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new NoSuchElementException("해당 inviteCode로 Room을 찾을 수 없습니다."));
    }


}
