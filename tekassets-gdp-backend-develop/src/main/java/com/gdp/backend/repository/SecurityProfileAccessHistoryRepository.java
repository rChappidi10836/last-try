package com.gdp.backend.repository;

import com.gdp.backend.model.SecurityProfileAccessHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityProfileAccessHistoryRepository extends JpaRepository<SecurityProfileAccessHistory, Integer> {
}
