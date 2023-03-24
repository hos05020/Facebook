package com.example.facebook.post.comment;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.example.facebook.post.comment.QCommentDto is a Querydsl Projection type for CommentDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QCommentDto extends ConstructorExpression<CommentDto> {

    private static final long serialVersionUID = 1282223302L;

    public QCommentDto(com.querydsl.core.types.Expression<? extends Comment> comment, com.querydsl.core.types.Expression<? extends com.example.facebook.user.User> user) {
        super(CommentDto.class, new Class<?>[]{Comment.class, com.example.facebook.user.User.class}, comment, user);
    }

}

