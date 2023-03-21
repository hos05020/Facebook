package com.example.facebook.user.connection;

import com.example.facebook.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConnectionsRepository extends JpaRepository<Connections,Long>,ConnectionsRepositoryCustom {

    @Query("select c.targetSeq from Connections c where c.userSeq = :userId and  c.grantedAt is not null order by c.targetSeq.seq desc")
    List<User> findConnectedIds(@Param("userId") Long userId);

}
