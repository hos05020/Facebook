package com.example.facebook.post.like;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLike is a Querydsl query type for Like
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLike extends EntityPathBase<Like> {

    private static final long serialVersionUID = -2003720425L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLike like = new QLike("like1");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final com.example.facebook.post.QPost post;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final com.example.facebook.user.QUser user;

    public QLike(String variable) {
        this(Like.class, forVariable(variable), INITS);
    }

    public QLike(Path<? extends Like> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLike(PathMetadata metadata, PathInits inits) {
        this(Like.class, metadata, inits);
    }

    public QLike(Class<? extends Like> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.example.facebook.post.QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.example.facebook.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

