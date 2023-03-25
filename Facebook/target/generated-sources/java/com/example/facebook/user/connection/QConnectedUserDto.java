package com.example.facebook.user.connection;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.example.facebook.user.connection.QConnectedUserDto is a Querydsl Projection type for ConnectedUserDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QConnectedUserDto extends ConstructorExpression<ConnectedUserDto> {

    private static final long serialVersionUID = -1682095153L;

    public QConnectedUserDto(com.querydsl.core.types.Expression<? extends Connections> connections, com.querydsl.core.types.Expression<? extends com.example.facebook.user.User> user) {
        super(ConnectedUserDto.class, new Class<?>[]{Connections.class, com.example.facebook.user.User.class}, connections, user);
    }

}

