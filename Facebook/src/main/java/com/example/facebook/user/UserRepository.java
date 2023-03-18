package com.example.facebook.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>,UserRepositoryCustom {


    Optional<User> findByEmail(Email email);
}
