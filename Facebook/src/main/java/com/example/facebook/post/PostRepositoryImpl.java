package com.example.facebook.post;

import static com.example.facebook.post.QPost.post;
import static com.example.facebook.post.like.QLikes.likes;
import static com.example.facebook.user.QUser.user;

import com.example.facebook.common.Id;
import com.example.facebook.user.User;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl implements PostRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<Post> findById(Id<Post, Long> postId, Id<User, Long> writerId,
        Id<User, Long> userId) {
       Post findPost = jpaQueryFactory.select(
                QPost.create(post,user,ExpressionUtils.as(likes.seq.isNotNull(),"likesOfMe")))
            .from(post)
            .join(post.user, user)
            .leftJoin(likes)
            .on(likes.post.seq.eq(postId.value()).and(likes.user.seq.eq(userId.value())))
            .where(post.seq.eq(postId.value()).and(post.user.seq.eq(writerId.value()))).fetchOne();

        return Optional.ofNullable(findPost);
    }

    @Override
    public List<PostDto> findAll(Id<User, Long> writerId, Id<User, Long> userId, long offset,
        int limit) {
        return jpaQueryFactory.select(
                new QPostDto(post,user,ExpressionUtils.as(likes.seq.isNotNull(),"likesOfMe")))
            .from(post)
            .join(post.user, user)
            .leftJoin(likes)
            .on(likes.post.seq.eq(post.seq).and(likes.user.seq.eq(userId.value())))
            .where(post.user.seq.eq(writerId.value())).orderBy(post.seq.desc())
            .offset(offset)
            .limit(limit)
            .fetch();

    }
}
