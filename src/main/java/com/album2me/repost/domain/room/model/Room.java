package com.album2me.repost.domain.room.model;

import com.album2me.repost.domain.member.domain.Member;
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

    @Column(length = 50, nullable = false, unique = true)
    private String inviteCode;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomApply> roomApplies = new ArrayList<>();

    public Room(String name) {
        generateInviteCode();
        this.name = name;
    }

    public void generateInviteCode() {
        this.inviteCode = RandomStringUtils.randomAlphabetic(30);;
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void addApply(RoomApply roomApply) {
        roomApplies.add(roomApply);
    }
}
