package com.example.facebook.user.connection;

import static com.example.facebook.user.QUser.*;
import static com.example.facebook.user.connection.QConnections.*;
import static java.util.stream.Collectors.*;

import com.example.facebook.common.Id;
import com.example.facebook.user.QUser;
import com.example.facebook.user.User;
import com.example.facebook.user.connection.QConnections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;

public class ConnectionsRepositoryImpl implements ConnectionsRepositoryCustom{


    private final JPAQueryFactory jpaQueryFactory;

    public ConnectionsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ConnectedUser> findAllConnectedUser(Id<User, Long> userId) {
        List<Connections> connections = jpaQueryFactory.select(QConnections.connections).from(
                QConnections.connections)
            .join(QConnections.connections.targetSeq, user)
            .where(
                QConnections.connections.userSeq.seq.eq(userId.value()).and(
                    QConnections.connections.grantedAt.isNotNull()))
            .fetch();

        return connections.stream().map(ConnectedUser::new).collect(toList());
    }
}
