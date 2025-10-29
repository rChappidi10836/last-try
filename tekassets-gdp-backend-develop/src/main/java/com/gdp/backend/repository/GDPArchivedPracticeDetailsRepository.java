package com.gdp.backend.repository;

import com.gdp.backend.model.GDPArchivedPracticeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GDPArchivedPracticeDetailsRepository extends JpaRepository<GDPArchivedPracticeDetails, String> {
}