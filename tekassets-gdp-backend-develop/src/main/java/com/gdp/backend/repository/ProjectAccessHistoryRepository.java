package com.gdp.backend.repository;

import com.gdp.backend.model.ProjectAccessHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAccessHistoryRepository extends JpaRepository<ProjectAccessHistory, Integer> {

}
