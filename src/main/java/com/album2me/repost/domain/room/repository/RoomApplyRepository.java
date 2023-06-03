package com.album2me.repost.domain.room.repository;

import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.room.model.RoomApply;
import com.album2me.repost.domain.room.model.RoomApplyStatus;
import com.album2me.repost.domain.user.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomApplyRepository extends JpaRepository<RoomApply, Long> {
    @Query("select a from RoomApply a join fetch a.requester where a.room = :room and a.roomApplyStatus = :status")
    List<RoomApply> findRoomAppliesWithUserByRoomAndRoomApplyStatus(Room room, RoomApplyStatus status);

    @Query("select a from RoomApply a join fetch a.requester where a.id = :id")
    Optional<RoomApply> findRoomApplyWithUserById(Long id);

    boolean existsByRoomAndRequesterAndRoomApplyStatus(Room room, User user, RoomApplyStatus status);
}
