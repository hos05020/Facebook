package com.example.facebookapiserver.user.connection;

import static com.example.facebook.user.QUser.*;
import static com.example.facebook.user.connection.QConnections.*;

import com.example.facebook.common.Id;
import com.example.facebook.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;

public class ConnectionsRepositoryImpl implements ConnectionsRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;

    public ConnectionsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<Long> findConnectedIds(Id<User, Long> userId) {
        return jpaQueryFactory.select(connections.targetSeq.seq).from(connections).where(
                connections.userSeq.seq.eq(userId.value()).and(connections.grantedAt.isNotNull()))
            .orderBy(connections.targetSeq.seq.desc())
            .fetch();
        //  리스트 조회, 데이터 없으면 빈 리스트 반환
    }

    @Override
    public List<ConnectedUserDto> findAllConnectedUser(Id<User, Long> userId) {

        return jpaQueryFactory.select(
                new QConnectedUserDto(connections, connections.targetSeq))
            .from(connections)
            .join(connections.targetSeq, user)
            .where(
                connections.userSeq.seq.eq(userId.value()).and(
                    connections.grantedAt.isNotNull()))
            .fetch();         //  리스트 조회, 데이터 없으면 빈 리스트 반환
    }
}
