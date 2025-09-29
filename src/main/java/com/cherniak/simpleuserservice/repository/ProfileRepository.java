package com.cherniak.simpleuserservice.repository;

import com.cherniak.simpleuserservice.model.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {


    boolean existsByUserId(Long userId);

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = "profileAddresses")
    Optional<Profile> findByUserId(Long userId);
}

