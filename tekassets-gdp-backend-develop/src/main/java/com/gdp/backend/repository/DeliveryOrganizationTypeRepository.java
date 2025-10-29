package com.gdp.backend.repository;

import com.gdp.backend.model.DeliveryOrganizationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryOrganizationTypeRepository extends JpaRepository<DeliveryOrganizationType, Integer> {
    Optional<DeliveryOrganizationType> findByDeliveryOrganization(String name);
}
