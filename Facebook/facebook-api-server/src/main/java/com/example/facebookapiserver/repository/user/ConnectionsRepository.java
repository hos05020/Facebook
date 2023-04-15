package com.example.facebookapiserver.repository.user;

import com.example.facebookapiserver.domain.user.Connections;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionsRepository extends JpaRepository<Connections,Long>,ConnectionsRepositoryCustom {

}
