package com.example.facebookapiserver.user.connection;

import com.example.facebook.common.Id;
import com.example.facebook.user.User;
import java.util.List;

public interface ConnectionsRepositoryCustom {

    List<ConnectedUserDto> findAllConnectedUser(Id<User,Long> userId);

    List<Long> findConnectedIds(Id<User,Long> userId);

}
