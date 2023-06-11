package com.album2me.repost.domain.post.repository;

import com.album2me.repost.domain.image.model.QImage;
import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.post.model.QPost;
import com.album2me.repost.domain.room.model.QRoom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.album2me.repost.domain.post.model.QPost.post;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<Post> findAllPostWithImage(final Long roomId, final Long cursorId, final Pageable pageable) {
        final QPost post = QPost.post;
        final QRoom room = QRoom.room;

        List<Post> results = jpaQueryFactory.selectFrom(post)
                .join(post.room, room)
                .fetchJoin()
                .where(ltCursorId(cursorId))
                .where(room.id.eq(roomId))
                .orderBy(post.createdAt.desc())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(results);
    }

    @Override
    public Slice<Post> findFirstImageUrlsForPosts(final Long roomId, final Long cursorId, Pageable pageable) {
        final QPost post = QPost.post;
        final QRoom room = QRoom.room;
        final QImage image = QImage.image;

        List<Post> results = jpaQueryFactory.selectFrom(post)
                .join(post.room, room)
                .fetchJoin()
                .join(post.images, image)
                .fetchJoin()
                .where(room.id.eq(roomId))
                .where(ltCursorId(cursorId))
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(results);
    }

    private BooleanExpression ltCursorId(final Long cursorId) {
        if (cursorId != null) {
            return post.id.lt(cursorId);
        }

        return null;
    }
}
