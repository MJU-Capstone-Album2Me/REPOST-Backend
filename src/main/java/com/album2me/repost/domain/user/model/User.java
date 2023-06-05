package com.album2me.repost.domain.user.model;

import com.album2me.repost.domain.member.domain.Member;
import com.album2me.repost.global.common.BaseTimeColumn;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @Column(unique = true)
    private String profileImageUrl;

    @OneToMany(mappedBy = "user")
    List<Member> members = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @Builder
    protected User(final String authId, final String password, final String nickName) {
        this.authId = authId;
        this.password = password;
        this.nickName = nickName;
    }

    public void updateProfileImage(final String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
