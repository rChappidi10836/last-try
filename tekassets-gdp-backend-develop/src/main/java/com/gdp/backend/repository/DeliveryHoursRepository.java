package com.gdp.backend.repository;

import com.gdp.backend.common.Queries;
import com.gdp.backend.model.DeliveryHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryHoursRepository extends JpaRepository<DeliveryHours, Integer> {
    public List<DeliveryHours> findByProjectId(Integer projectId);

    public Integer deleteByProjectId(Integer projectId);

    @Query(value = Queries.FIND_LAST_DH_FOR_RESOURCE, nativeQuery = true)
    public Integer findLastDeliveryHourForResource(int projectId, int resourceId);
}
