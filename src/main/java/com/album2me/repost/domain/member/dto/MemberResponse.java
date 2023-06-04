package com.album2me.repost.domain.member.dto;

import com.album2me.repost.domain.member.domain.Member;
import java.time.LocalDate;

public record MemberResponse(
        Long id,
        String name,
        String profile_url,
        String joinedAt
) {
    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getUser().getNickName(),
                member.getUser().getProfileImageUrl(), member.getCreatedAt().toLocalDate().toString());
    }
}
