package com.album2me.repost.domain.room.repository;

import com.album2me.repost.domain.room.model.Room;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByInviteCode(String inviteCode);

}
