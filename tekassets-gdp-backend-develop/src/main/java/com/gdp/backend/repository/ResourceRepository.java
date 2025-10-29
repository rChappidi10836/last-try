package com.gdp.backend.repository;

import com.gdp.backend.common.Queries;
import com.gdp.backend.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    @Query(value="SELECT * from Resource R WHERE R.EmployeeId=(?1) AND R.OpcoId=(?2)",nativeQuery = true)
    public Optional<Resource> findByEmployeeId(String employeeId, Integer OpcoId);

    public Optional<Resource> findByUserId(String userId);

    @Query(value="SELECT * from Resource R WHERE R.UserId=(?1) AND R.OpcoId=(?2)",nativeQuery = true)
    public Optional<Resource> findByUserIdWithOpcoId(String userId,Integer OpcoId);

    @Query(value = Queries.FIND_FULLNAME_FROM_USER_ID,nativeQuery = true)
    public String findFullNameFromUserId(String userId);
    @Query(value = "SELECT R.OpcoId from Resource R JOIN Opco O ON R.OpcoId = O.Id WHERE O.OpcoName = ?1 and R.EmployeeId = ?1",nativeQuery = true)
    public String findOpcoIdByRegion(String opcoId,String empId);



}
