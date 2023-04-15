package com.example.facebookapiserver.repository.post;

import static com.example.facebookapiserver.domain.post.QLike.like;
import static com.example.facebookapiserver.domain.post.QPost.*;
import static com.example.facebookapiserver.domain.post.QPost.post;
import static com.example.facebookapiserver.domain.user.QUser.user;
import static com.querydsl.core.types.ExpressionUtils.as;

import com.example.facebookapiserver.controller.post.QPostDto;
import com.example.facebookapiserver.domain.common.Id;
import com.example.facebookapiserver.controller.post.PostDto;
import com.example.facebookapiserver.domain.post.Post;
import com.example.facebookapiserver.domain.post.QPost;
import com.example.facebookapiserver.domain.user.User;
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
                create(post, user, as(like.seq.isNotNull(), "likesOfMe")))
            .from(post)
            .join(post.user, user)
            .leftJoin(like)
            .on(like.post.seq.eq(postId.value()).and(like.user.seq.eq(userId.value())))
            .where(post.seq.eq(postId.value()).and(post.user.seq.eq(writerId.value()))).fetchOne();
        //단 건 조회, 없으면  null 반환

        return Optional.ofNullable(findPost);
    }

    @Override
    public List<PostDto> findAll(Id<User, Long> writerId, Id<User, Long> userId, long offset,
        int limit) {
        return jpaQueryFactory.select(
                new QPostDto(post, user, as(like.seq.isNotNull(), "likesOfMe")))
            .from(post)
            .join(post.user, user)
            .leftJoin(like)
            .on(like.post.seq.eq(post.seq).and(like.user.seq.eq(userId.value())))
            .where(post.user.seq.eq(writerId.value())).orderBy(post.seq.desc())
            .offset(offset)
            .limit(limit)
            .fetch(); //  리스트 조회, 데이터 없으면 빈 리스트 반환

    }

    @Override
    public Optional<User> findWriter(Id<Post, Long> postId) {
        return Optional.ofNullable(jpaQueryFactory.select(post.user).from(post).join(post.user, user)
            .where(post.seq.eq(postId.value())).fetchOne());
    }

    @Override
    public void update(Post post) {
        jpaQueryFactory.update(QPost.post)
            .set(QPost.post.contents, post.getContents())
            .set(QPost.post.likes, post.getLikes())
            .set(QPost.post.comments, post.getComments())
            .where(QPost.post.seq.eq(post.getSeq()))
            .execute();
    }
}
