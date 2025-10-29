package com.gdp.backend.repository;

import com.gdp.backend.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {

    public Opportunity findByOpportunityId(String opportunityId);

    public Optional<Opportunity> findById(Integer id);

    @Query(value="select O.Target_Technology_Platform from Opportunity O where O.OpportunityId=?1",nativeQuery = true)
    public String getTargetTechnologyPlatform(String trimmedId);
}