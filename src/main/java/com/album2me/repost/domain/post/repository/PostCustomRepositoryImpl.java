package com.album2me.repost.domain.post.repository;

import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.post.model.QPost;
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
    public Slice<Post> findAllPostWithImage(final Long cursorId, final Pageable pageable) {
        final QPost post = QPost.post;

        List<Post> results = jpaQueryFactory.selectFrom(post)
                .fetchJoin()
                .where(eqCursorId(cursorId))
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(results);
    }

    private BooleanExpression eqCursorId(final Long cursorId) {
        if (cursorId != null) {
            return post.id.gt(cursorId);
        }

        return null;
    }
}
