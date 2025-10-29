package com.gdp.backend.repository;

import com.gdp.backend.model.DeliveryMilestoneType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryMilestonesTypeRepository extends JpaRepository<DeliveryMilestoneType, Integer> {

    @Query(
            value = "SELECT * FROM DeliveryMilestoneType u WHERE u.Active=1 AND u.Deleted = 0 ORDER BY u.DeliveryMilestoneTypeName",
            nativeQuery = true)
    List<DeliveryMilestoneType> findAll();
}
