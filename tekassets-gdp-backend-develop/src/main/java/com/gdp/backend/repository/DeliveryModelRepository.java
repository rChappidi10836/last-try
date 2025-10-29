package com.gdp.backend.repository;

import com.gdp.backend.model.DeliveryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryModelRepository extends JpaRepository<DeliveryModel, Integer> {

}
