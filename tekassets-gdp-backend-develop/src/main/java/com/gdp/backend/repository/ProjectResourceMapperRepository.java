package com.gdp.backend.repository;

import com.gdp.backend.common.Queries;
import com.gdp.backend.model.ProjectResourceMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectResourceMapperRepository extends JpaRepository<ProjectResourceMapper, Integer> {

    @Modifying
    @Transactional
    @Query(value = Queries.DELETE_RESOURCE_MAPPER_BY_PROJECT_ID, nativeQuery = true)
    public void deleteByProjectId(int projectId);


    @Modifying
    @Transactional
    @Query(value = Queries.MARK_AS_OTHER_TEAM_MEMBER, nativeQuery = true)
    public void markAsOtherTeamMember(int projectId, int resourceId, int resourceRoleId);

}
