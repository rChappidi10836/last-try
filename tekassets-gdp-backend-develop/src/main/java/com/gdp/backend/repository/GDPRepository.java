package com.gdp.backend.repository;
import com.gdp.backend.model.GDPIds;
import com.gdp.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public interface GDPRepository extends JpaRepository<GDPIds, Integer>{
    @Modifying
    @Query(value = "select Id, GDPId from Project where Active=1", nativeQuery = true)
    List<GDPIds> searchValues();

    @Modifying
    @Query(value = "select GDPId from Project where id in (?1)", nativeQuery = true)
    ArrayList<String> GDPSearchDeletedValues(ArrayList<Integer> GdpId);

}