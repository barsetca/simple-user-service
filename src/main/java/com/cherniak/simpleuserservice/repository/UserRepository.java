package com.cherniak.simpleuserservice.repository;

import com.cherniak.simpleuserservice.dto.UserInfo;
import com.cherniak.simpleuserservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsernameAndDeletedAtIsNull(String username);

    boolean existsByEmailAndDeletedAtIsNull(String email);

    @Query("SELECT u.username as username, u.email as email  FROM User u WHERE u.id = :id")
    Optional<UserInfo> findUserInfoById(@Param("id") Long id);
}
