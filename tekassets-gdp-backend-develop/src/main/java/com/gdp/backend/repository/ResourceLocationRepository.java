package com.gdp.backend.repository;

import com.gdp.backend.model.ResourceLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceLocationRepository extends JpaRepository<ResourceLocation, Integer> {
}
