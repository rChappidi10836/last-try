package com.gdp.backend.repository;

import com.gdp.backend.model.ProjectRiskSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRiskSurveyRepository extends JpaRepository<ProjectRiskSurvey, Integer> {

    public Optional<ProjectRiskSurvey> findByProjectId(Integer projectId);
}
