package com.album2me.repost.domain.room.model;

import com.album2me.repost.domain.member.Member;
import com.album2me.repost.global.common.BaseTimeColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseTimeColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    private String inviteLink;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<EntranceRequest> entranceRequests = new ArrayList<>();

    public Room(String name) {
        generateInviteLink();
        this.name = name;
    }

    public void generateInviteLink() {
        this.inviteLink = RandomStringUtils.randomAlphabetic(30);;
    }

    public void addMember(Member member) {
        members.add(member);
    }
}
