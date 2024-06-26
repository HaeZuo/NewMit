package com.haezuo.newmit.login.repository;

import com.haezuo.newmit.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndOauthProvider(String email, String oauthProvider);

}

