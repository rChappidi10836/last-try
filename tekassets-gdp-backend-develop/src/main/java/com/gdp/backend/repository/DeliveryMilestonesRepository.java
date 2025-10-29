package com.gdp.backend.repository;

import com.gdp.backend.model.DeliveryMilestones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryMilestonesRepository extends JpaRepository<DeliveryMilestones, Integer> {

}
