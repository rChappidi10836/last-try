package com.gdp.backend.repository;

import com.gdp.backend.model.ProjectSecurityProfile;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;

import java.util.List;

public interface ProjectSecurityProfileRepository extends JpaRepository<ProjectSecurityProfile, Integer> {

    public List<ProjectSecurityProfile> findAllByProjectId(Integer projectId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ProjectSecurityProfile WHERE ProjectId = ?1", nativeQuery = true)
    public void deleteByProjectId(int projectId);



    @Query(value = "SELECT MAX(CreatedBy) FROM ProjectSecurityProfile WHERE ProjectId = :projectId", nativeQuery = true)
    String findCreatedBy(@Param("projectId") int projectId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ProjectSecurityProfile (ProjectId, SecurityAnswerId, Selected, Comments, CreatedAt, CreatedBy, ModifiedAt, ModifiedBy) " +
            "VALUES (:projectId, :securityId, 0, NULL, CURRENT_TIMESTAMP, :createdBy , CURRENT_TIMESTAMP, :modifiedBy)", nativeQuery = true)
    void insertSecurityAnswer(@Param("projectId") int projectId, @Param("securityId") Integer securityId, @Param("createdBy") String createdBy, @Param("modifiedBy") String modifiedBy);


//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO ProjectSecurityProfile (ProjectId, SecurityAnswerId, Selected, Comments, CreatedAt, ModifiedAt) " +
//            "VALUES (:projectId, :securityId, 0, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)", nativeQuery = true)
//    void insertSecurityAnswer(@Param("projectId") int projectId, @Param("securityId") Integer securityId);
}
