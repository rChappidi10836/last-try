package com.gdp.backend.repository;

import com.gdp.backend.model.ProjectLocationMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProjectLocationMapperRepository extends JpaRepository<ProjectLocationMapper, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ProjectLocationMapper WHERE ProjectId = ?1", nativeQuery = true)
    public void deleteByProjectId(int projectId);

}
