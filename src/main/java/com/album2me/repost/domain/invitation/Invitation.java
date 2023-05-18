package com.album2me.repost.domain.invitation;

import java.time.LocalDateTime;
import org.apache.commons.lang3.RandomStringUtils;

public class Invitation {
    private static final int CODE_LENGTH = 20;
    private static final int EXPIRES_DAY = 7;
    private final Long roomId;
    private final Long memberId;
    private final String code;
    private final LocalDateTime expiresAt;

    public static Invitation generate(final Long roomId, final Long memberId) {
        String code = RandomStringUtils.randomAlphabetic(CODE_LENGTH);
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(EXPIRES_DAY);
        return new Invitation(roomId, memberId, code, expiresAt);
    }

    private Invitation(Long roomId, Long memberId, String code, LocalDateTime expiresAt) {
        this.roomId = roomId;
        this.memberId = memberId;
        this.code = code;
        this.expiresAt = expiresAt;
    }
}
