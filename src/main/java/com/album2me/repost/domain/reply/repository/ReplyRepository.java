package com.album2me.repost.domain.reply.repository;

import com.album2me.repost.domain.reply.domain.Reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
