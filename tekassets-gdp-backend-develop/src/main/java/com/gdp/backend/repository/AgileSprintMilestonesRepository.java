package com.gdp.backend.repository;

import com.gdp.backend.model.AgileSprintMilestones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgileSprintMilestonesRepository extends JpaRepository<AgileSprintMilestones, Integer> {

}
