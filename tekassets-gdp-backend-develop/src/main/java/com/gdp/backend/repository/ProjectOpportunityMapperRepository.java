package com.gdp.backend.repository;

import com.gdp.backend.model.ProjectOpportunityMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface ProjectOpportunityMapperRepository extends
        JpaRepository<ProjectOpportunityMapper, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ProjectOpportunityMapper WHERE ProjectId = ?1", nativeQuery = true)
    public void deleteByProjectId(int projectId);
}
