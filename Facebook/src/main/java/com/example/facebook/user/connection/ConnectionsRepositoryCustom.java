package com.example.facebook.user.connection;

import com.example.facebook.common.Id;
import com.example.facebook.user.User;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface ConnectionsRepositoryCustom {

    List<ConnectedUser> findAllConnectedUser(Id<User,Long> userId);

    List<Long> findConnectedIds(Id<User,Long> userId);

}
