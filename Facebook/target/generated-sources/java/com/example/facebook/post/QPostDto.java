package com.example.facebook.post;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.example.facebook.post.QPostDto is a Querydsl Projection type for PostDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QPostDto extends ConstructorExpression<PostDto> {

    private static final long serialVersionUID = -1673698648L;

    public QPostDto(com.querydsl.core.types.Expression<? extends Post> post, com.querydsl.core.types.Expression<? extends com.example.facebook.user.User> user, com.querydsl.core.types.Expression<Boolean> likesOfMe) {
        super(PostDto.class, new Class<?>[]{Post.class, com.example.facebook.user.User.class, boolean.class}, post, user, likesOfMe);
    }

}

