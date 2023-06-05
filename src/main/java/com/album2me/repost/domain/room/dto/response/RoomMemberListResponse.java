package com.album2me.repost.domain.room.dto.response;

import com.album2me.repost.domain.member.dto.MemberResponse;
import java.util.List;

public record RoomMemberListResponse(
        List<MemberResponse> members
) {
}
