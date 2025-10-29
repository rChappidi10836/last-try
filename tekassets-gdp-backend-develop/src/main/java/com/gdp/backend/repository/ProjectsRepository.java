package com.gdp.backend.repository;

import com.gdp.backend.common.Queries;
import com.gdp.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Integer> {

    public boolean existsByProjectName(String projectName);

    public Optional<Project> findById(Integer projectId);




    public Optional<List<Project>> findByDeliveryModelId(Integer id);

    public Optional<List<Project>> findByDeliveryOrganizationTypeId(Integer id);

    @Query(value="select P.Target_Technology_Platform from Project P where P.Id=?1",nativeQuery = true)
    public String getTargetTechnologyPlatform(Integer Id);



    @Query(value = "Select COUNT(*) From Project P JOIN Client C ON C.Id = P.ClientId JOIN ProjectStatus PS ON PS.ProjectId = P.Id JOIN ( SELECT ProjectStatus.ProjectId, MAX(ProjectStatus.WeekEndingDate) WED From ProjectStatus WHERE ProjectStatus.Active = 1 Group By ProjectId ) Res ON PS.ProjectId = Res.ProjectId AND PS.WeekEndingDate = Res.WED WHERE PS.Active = 1 AND P.Active = 1 AND C.OpcoId=?1", nativeQuery = true)
    public long countDefaultProjectList(int OpcoId);

    @Modifying
    @Query(value = "UPDATE Project SET Active = 0 WHERE Id = ?1", nativeQuery = true)
    public void setProjectInActive(int id);

    @Modifying
    @Query(value = "UPDATE Project SET Active = 1 WHERE Id = ?1", nativeQuery = true)
    public void setProjectActive(int id);

    @Query(value = "SELECT C.OpcoId from Client C JOIN Project P ON C.Id = P.ClientId WHERE P.Id = ?1", nativeQuery = true)
    public  String findOpcoId(int id);
}
