package com.gdp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gdp.backend.model.PrimaryServiceType;

@Repository
public interface PrimaryServiceTypeRepository extends JpaRepository<PrimaryServiceType, Integer> { 

}
