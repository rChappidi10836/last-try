package com.gdp.backend.repository;

import com.gdp.backend.model.GDPUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GDPUserRepository extends JpaRepository<GDPUser, Integer> {
    Optional<GDPUser> findByUsername(String username);
    @Query(value = "SELECT * FROM GDPUser g WHERE g.Username LIKE ?1%",
            nativeQuery = true)
    Optional<GDPUser> findByUsernameLike(String username);
}