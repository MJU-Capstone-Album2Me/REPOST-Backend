package com.album2me.repost.domain.user.model;

import com.album2me.repost.global.common.BaseTimeColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
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

    @Builder
    protected User(String authId, String password, String nickName) {
        this.authId = authId;
        this.password = password;
        this.nickName = nickName;
    }
}
