package com.album2me.repost.domain.room.repository;

import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.room.model.RoomApply;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomApplyRepository extends JpaRepository<RoomApply, Long> {
    @Query("select a from RoomApply a join fetch User u where a.room = :room")
    List<RoomApply> findRoomAppliesWithUserByRoom(Room room);

    @Query("select a from RoomApply a join fetch User u where a.id = :id")
    Optional<RoomApply> findRoomApplyWithUserById(Long id);
}
