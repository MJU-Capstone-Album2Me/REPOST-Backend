package com.album2me.repost.domain.room.service;

import com.album2me.repost.domain.member.domain.Member;
import com.album2me.repost.domain.member.service.MemberService;
import com.album2me.repost.domain.room.dto.request.RoomApplyApproveRequest;
import com.album2me.repost.domain.room.dto.request.RoomCreateRequest;
import com.album2me.repost.domain.room.dto.response.RoomApplyListResponse;
import com.album2me.repost.domain.room.dto.response.RoomApplyResponse;
import com.album2me.repost.domain.room.dto.response.RoomCreateResponse;
import com.album2me.repost.domain.room.dto.response.RoomInviteCodeResponse;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.room.model.RoomApply;
import com.album2me.repost.domain.room.model.RoomApplyStatus;
import com.album2me.repost.domain.room.repository.RoomApplyRepository;
import com.album2me.repost.domain.room.repository.RoomRepository;

import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.service.UserService;
import java.util.List;
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
    private final RoomApplyRepository roomApplyRepository;
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
        checkAlreadyApplied(room, requester);
        room.addApply(new RoomApply(room, requester));
    }

    public RoomApplyListResponse getApplications(Long roomId, Long userId) {
        Room room = findRoomById(roomId);
        User user = userService.findUserById(userId);
        memberService.checkHost(room, user);
        List<RoomApplyResponse> roomApplyResponseList = roomApplyRepository.findRoomAppliesWithUserByRoomAndRoomApplyStatus(room, RoomApplyStatus.WAITING)
                .stream().map(RoomApplyResponse::from).toList();
        return new RoomApplyListResponse(roomApplyResponseList);
    }

    @Transactional
    public void approveApply(RoomApplyApproveRequest roomApplyApproveRequest, Long roomId, Long userId) {
        Room room = findRoomById(roomId);
        User user = userService.findUserById(userId);
        memberService.checkHost(room, user);
        RoomApply roomapply = findRoomApplyWithUserById(roomApplyApproveRequest.applyId());
        roomapply.approve();
        room.addMember(new Member(roomapply.getRequester(), room, false));
        roomApplyRepository.save(roomapply);
        roomRepository.save(room);
    }

    public Room findRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 Room을 찾을 수 없습니다."));
    }

    public Room findRoomByInviteCode(String inviteCode) {
        return roomRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new NoSuchElementException("해당 inviteCode로 Room을 찾을 수 없습니다."));
    }

    public RoomApply findRoomApplyWithUserById(Long applyId) {
        return roomApplyRepository.findRoomApplyWithUserById(applyId)
                .orElseThrow(() -> new NoSuchElementException("RoomApply가 존재하지 않습니다."));
    }

    public void checkAlreadyApplied(Room room, User requester) {
        if(roomApplyRepository.existsByRoomAndRequesterAndRoomApplyStatus(room, requester, RoomApplyStatus.WAITING)){
            throw new IllegalArgumentException("이미 지원한 상태입니다.");
        }
    }

}
