package com.example.facebook.post.comment;

import static com.example.facebook.post.comment.QComment.*;
import static com.example.facebook.user.QUser.*;

import com.example.facebook.common.Id;
import com.example.facebook.post.Post;
import com.example.facebook.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;

public class CommentRepositoryImpl implements CommentRepositoryCustom{


    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<CommentDto> findAll(Id<Post, Long> postId) {
        return jpaQueryFactory.select(new QCommentDto(comment,comment.user))
            .from(comment)
            .join(comment.user, user)
            .where(comment.post.seq.eq(postId.value()))
            .orderBy(comment.seq.desc())
            .fetch();
    }
}
