package com.example.facebook.user.connection;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConnections is a Querydsl query type for Connections
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConnections extends EntityPathBase<Connections> {

    private static final long serialVersionUID = 232450513L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConnections connections = new QConnections("connections");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> grantedAt = createDateTime("grantedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final com.example.facebook.user.QUser targetSeq;

    public final com.example.facebook.user.QUser userSeq;

    public QConnections(String variable) {
        this(Connections.class, forVariable(variable), INITS);
    }

    public QConnections(Path<? extends Connections> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConnections(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConnections(PathMetadata metadata, PathInits inits) {
        this(Connections.class, metadata, inits);
    }

    public QConnections(Class<? extends Connections> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.targetSeq = inits.isInitialized("targetSeq") ? new com.example.facebook.user.QUser(forProperty("targetSeq"), inits.get("targetSeq")) : null;
        this.userSeq = inits.isInitialized("userSeq") ? new com.example.facebook.user.QUser(forProperty("userSeq"), inits.get("userSeq")) : null;
    }

}

