package com.album2me.repost.domain.member.repository;

import com.album2me.repost.domain.member.domain.Member;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByRoomAndUser(Room room, User user);
}
