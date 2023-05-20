package com.album2me.repost.domain.room.dto.response;

import com.album2me.repost.domain.room.model.RoomApply;
import java.time.LocalDateTime;

public record RoomApplyResponse (
        Long id,
        String nickName,
        int pastHour
){
    public static RoomApplyResponse from(RoomApply roomApply) {
        return new RoomApplyResponse(roomApply.getId(),
                roomApply.getRequester().getNickName(),
                LocalDateTime.now().getHour() - roomApply.getRequestedAt().getHour());
    }
}
