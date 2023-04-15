package com.example.facebookapiserver.repository.user;

import com.example.facebookapiserver.domain.user.Email;
import com.example.facebookapiserver.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom {


    Optional<User> findByEmail(Email email);
}
