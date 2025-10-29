package com.gdp.backend.repository;

import com.gdp.backend.model.Opco;
import com.gdp.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcoRepository extends JpaRepository<Opco, Integer> {
}
