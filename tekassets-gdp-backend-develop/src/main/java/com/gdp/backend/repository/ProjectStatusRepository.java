package com.gdp.backend.repository;

import com.gdp.backend.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Integer> {

    public Optional<ProjectStatus> findByProject_IdAndWeekEndingDateAndIsActive(Integer projectId, Date weekEndingDate, Boolean isActive);

    public Optional<ProjectStatus> findByProject_IdAndWeekEndingDate(Integer projectId, Date weekEndingDate);

}

