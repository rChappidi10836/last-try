package com.gdp.backend.repository;

import com.gdp.backend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query(
            value = "SELECT * FROM Client c WHERE TRIM(c.ClientName)=(?1)  AND c.opcoId=(?2) AND c.Active=(?3) ",
            nativeQuery = true)
    public List<Client> findClientByOpco(String clientName, Integer Opco, Boolean Active);

    @Query(
            value = "SELECT * FROM Client c WHERE TRIM(c.ClientName)=(?1) AND c.Active = (?2) AND c.opcoId=(?3) AND c.Deleted=0",
            nativeQuery = true)
    public List<Client> findaccList(String AccountName, Boolean Active, Integer OpcoId);
}
