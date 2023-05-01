package com.album2me.repost.domain.user.repository;

import com.album2me.repost.domain.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAuthId(String authId);
}
