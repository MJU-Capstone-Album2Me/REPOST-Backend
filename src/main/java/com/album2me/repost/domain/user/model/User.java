package com.album2me.repost.domain.user.model;

import com.album2me.repost.global.common.BaseTimeColumn;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String authId;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, unique = true, nullable = false)
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @Builder
    protected User(final String authId, final String password, final String nickName) {
        this.authId = authId;
        this.password = password;
        this.nickName = nickName;
    }

}
