package com.album2me.repost.domain.room.service;

import com.album2me.repost.domain.member.domain.Member;
import com.album2me.repost.domain.member.dto.MemberResponse;
import com.album2me.repost.domain.member.service.MemberService;
import com.album2me.repost.domain.notification.service.NotificationService;
import com.album2me.repost.domain.room.dto.request.RoomApplyApproveRequest;
import com.album2me.repost.domain.room.dto.request.RoomCreateRequest;
import com.album2me.repost.domain.room.dto.response.*;
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
    private final NotificationService notificationService;

    @Transactional
    public RoomCreateResponse createRoom(RoomCreateRequest roomCreateRequest, Long userId) {
        User user = userService.findUserById(userId);
        Room newRoom = new Room(roomCreateRequest.name());
        Member host = new Member(user, newRoom, true);
        newRoom.addMember(host);
        roomRepository.save(newRoom);
        return new RoomCreateResponse(newRoom.getId());
    }

    public RoomListResponse getRooms(Long userId){
        User user = userService.findUserById(userId);
        List<Member> members = memberService.findMembersWithRoomByUser(user);
        return new RoomListResponse(members.stream().map(member ->
                new RoomResponse(member.getRoom().getId(), member.getRoom().getName(),
                        member.getRoom().getMembersCount())).toList());
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

        // 룸 가입 요청
        checkAlreadyApplied(room, requester);
        RoomApply roomApply = new RoomApply(room, requester);
        room.addApply(roomApply);

        // 호스트에게 알림 전송
        Member host = memberService.findHostWithUserByRoom(room);
        notificationService.createApplyNotification(host.getUser(), requester, roomApply);
    }

    @Transactional
    public void approveApply(RoomApplyApproveRequest roomApplyApproveRequest, Long roomApplyId, Long userId) {
        User user = userService.findUserById(userId);
        RoomApply roomApply = findRoomApplyWithUserById(roomApplyId);
        Room room = roomApply.getRoom();
        memberService.checkHost(room, user);
        // 요청을 수락 할 경우 멤버 추가 및 알림 보내기
        if(roomApplyApproveRequest.approveCheck()){
            room.addMember(new Member(roomApply.getRequester(), room, false));
            notificationService.createApplyApproveNotification(roomApply.getRequester(), room);
        }
        //승인이나 거절된 알림 및 요청 데이터 삭제
        notificationService.deleteNotification(roomApplyApproveRequest.notificationId());
        roomApplyRepository.delete(roomApply);
        roomRepository.save(room);
    }

    public RoomMemberListResponse getMembers(Long roomId, Long userId) {
        Room room = findRoomById(roomId);
        User user = userService.findUserById(userId);
        List<Member> members = memberService.findMembersWithUserByRoom(room);
        return new RoomMemberListResponse(members.stream().map(MemberResponse::of).toList());
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
        if(roomApplyRepository.existsByRoomAndRequester(room, requester)){
            throw new IllegalArgumentException("이미 지원한 상태입니다.");
        }
    }
}
