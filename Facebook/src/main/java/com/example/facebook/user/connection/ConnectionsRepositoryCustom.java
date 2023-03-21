package com.example.facebook.user.connection;

import com.example.facebook.common.Id;
import com.example.facebook.user.User;
import java.util.List;

public interface ConnectionsRepositoryCustom {

    List<ConnectedUser> findAllConnectedUser(Id<User,Long> userId);

}
