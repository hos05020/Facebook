package com.example.facebook.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -1473184841L;

    public static ConstructorExpression<Post> create(Expression<? extends Post> post, Expression<? extends com.example.facebook.user.User> user, Expression<Boolean> likesOfMe) {
        return Projections.constructor(Post.class, new Class<?>[]{Post.class, com.example.facebook.user.User.class, boolean.class}, post, user, likesOfMe);
    }

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final NumberPath<Integer> comments = createNumber("comments", Integer.class);

    public final StringPath contents = createString("contents");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final com.example.facebook.user.QUser user;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.facebook.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

