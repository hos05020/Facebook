package com.example.facebookapiserver.user.connection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionsRepository extends JpaRepository<Connections,Long>,ConnectionsRepositoryCustom {

}
