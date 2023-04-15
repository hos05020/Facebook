package com.example.facebookapiserver.repository.user;

import com.example.facebookapiserver.domain.common.Id;
import com.example.facebookapiserver.controller.user.ConnectedUserDto;
import com.example.facebookapiserver.domain.user.User;
import java.util.List;

public interface ConnectionsRepositoryCustom {

    List<ConnectedUserDto> findAllConnectedUser(Id<User,Long> userId);

    List<Long> findConnectedIds(Id<User,Long> userId);

}
